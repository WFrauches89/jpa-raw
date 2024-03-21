package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.*;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PerformanceSQL {

    public static void main(String[] args) {
        popularBD();
        EntityManager em = JPAUtil.getEntityManager();
        PedidoDao pdDao = new PedidoDao(em);
        ProdutoDao produtoDao = new ProdutoDao(em);

        System.out.println("----------------------------\n\n\n\n\n\n\n\n\n\n\n\n");
        var acer = produtoDao.buscarPorParamsPorCriteria("Acer", null, null);


        System.out.println("----------------------------\n\n\n\n\n\n\n\n\n\n\n\n");

        var clPdDao= pdDao.buscarPedidoCliente(1L);
        Pedido pd = em.find(Pedido.class, 1L);
        System.out.println(pd.getDataPedido());
        System.out.println("------------");
        System.out.println("------------");
        em.close();
        System.out.println("dados em Dao JOIN FETCH:"+clPdDao.getCliente().getNome()+ " "+clPdDao.getCliente().getCpf());
        System.out.println("Mesmo pelo find, como já foi carregado pelo JOIN FETCH está retornando..."+pd.getCliente().getCpf());



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
        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10, pedido, celular));
        pedido.adicionarItem(new ItemPedido(40, pedido, info));

        Pedido pedido2 = new Pedido(cliente);
        pedido2.adicionarItem(new ItemPedido(2, pedido, games));

        PedidoDao pedidoDao = new PedidoDao(em);

        em.getTransaction().begin();

        categoriaDao.cadastrar(celulares);
        categoriaDao.cadastrar(informatica);
        categoriaDao.cadastrar(videogames);
        produtoDao.cadastrar(celular);
        produtoDao.cadastrar(info);
        produtoDao.cadastrar(games);
        clienteDao.cadastrar(cliente);
        pedidoDao.cadastrar(pedido);
        pedidoDao.cadastrar(pedido2);


        em.getTransaction().commit();
        em.close();
    }
}
