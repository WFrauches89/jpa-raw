package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioVo;
import org.w3c.dom.ls.LSOutput;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class CadastroPedido {

    public static void main(String[] args) {
        popularBD();
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        ClienteDao clienteDao = new ClienteDao(em);
        Produto produto = produtoDao.buscarPorId(1L);
        Produto produto2 = produtoDao.buscarPorId(2L);
        Produto produto3 = produtoDao.buscarPorId(3L);
        Cliente cliente = clienteDao.buscarPorId(1L);

        em.getTransaction().begin();

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, produto));
        pedido.adicionarItem(new ItemPedido(40, pedido, produto2));

        Pedido pedido2 = new Pedido(cliente);
        pedido2.adicionarItem(new ItemPedido(2, pedido, produto3));

        PedidoDao pedidoDao = new PedidoDao(em);
        pedidoDao.cadastrar(pedido);
        pedidoDao.cadastrar(pedido2);


        em.getTransaction().commit();

        BigDecimal totalVendido = pedidoDao.valorTotalVendas();
        System.out.println("Valor total pedido: "+totalVendido);

        //primeiro modelo
        List<Object[]> rel = pedidoDao.relatorioVendas();

        rel.stream()
                .flatMap(Arrays::stream)
                .forEach(System.out::println);

        for (Object[] obj : rel){
            System.out.println(obj[0]);
            System.out.println(obj[1]);
            System.out.println(obj[2]);
        }

        //segundo modelo orientado a objeto
        var rel2 = pedidoDao.relatorioVendas2();
        rel2.forEach(System.out::println);


        em.close();

    }

    private static void popularBD() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria informatica = new Categoria("INFORMATICA");
        Categoria videogames = new Categoria("VIDEOGAME");
        Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares );
        Produto info = new Produto("Acer", "legal", new BigDecimal("1800"), informatica );
        Produto games = new Produto("XBOX SERIE X", "Top", new BigDecimal("3000"), videogames );
        Cliente cliente = new Cliente("João", "123456789");
        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(informatica);
        categoriaDao.cadastrar(videogames);
        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(info);
        produtoDao.cadastrar(games);
        clienteDao.cadastrar(cliente);

        em.getTransaction().commit();
        em.close();
    }

}
