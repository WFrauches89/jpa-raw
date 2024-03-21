package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}

	public BigDecimal valorTotalVendas() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";

		return em.createQuery(jpql, BigDecimal.class)
				.getSingleResult();
	}

	public List<Object[]> relatorioVendas() {
		String jpql = "SELECT pdt.nome, " +
				"SUM(it.quantidade), " +
				"MAX(pdd.dataPedido) " +
				"FROM Pedido pdd " +
				" JOIN pdd.itens it" +
				" JOIN it.produto pdt" +
				" GROUP BY pdt.nome" +
				" ORDER BY it.quantidade DESC";
		return em.createQuery(jpql, Object[].class)
				.getResultList();
	}

	public List<RelatorioVo> relatorioVendas2() {
		String jpql = "SELECT new br.com.alura.loja.vo.RelatorioVo(" +
				"pdt.nome, " +
				"SUM(it.quantidade) as quantidadeTotal, " +
				"MAX(pdd.dataPedido)) " +
				"FROM Pedido pdd " +
				" JOIN pdd.itens it" +
				" JOIN it.produto pdt" +
				" GROUP BY pdt.nome" +
				" ORDER BY quantidadeTotal DESC";
		return em.createQuery(jpql, RelatorioVo.class)
				.getResultList();
	}


	public Pedido buscarPedidoCliente (Long id) {//tratamento para evitar LazyException JOIN FETCH
		return em.createQuery("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.id = :id ",Pedido.class)
				.setParameter("id", id)
				.getSingleResult();
	}


}
