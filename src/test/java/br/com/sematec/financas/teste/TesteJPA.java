package br.com.sematec.financas.teste;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.sematec.financas.modelo.Agencia;
import br.com.sematec.financas.modelo.Conta;
import br.com.sematec.financas.modelo.Movimentacao;
import br.com.sematec.financas.modelo.Pessoa;
import br.com.sematec.financas.modelo.TipoMovimentacao;
import br.com.sematec.financas.util.JPAUtil;

public class TesteJPA {

	public static void main(String[] args) {

		double inicio = System.currentTimeMillis();

		Conta conta = new Conta();
		conta.setTitular("Joao Ferreira");
		conta.setBanco("HSBC");
		conta.setNumero("123345");
		
		Conta conta2 = new Conta();
		conta2.setTitular("Joao Ferreira");
		conta2.setBanco("HSBC");
		conta2.setNumero("123345");	
			
		Pessoa nova = new Pessoa();
		nova.setNome("daniel");
		conta.getListaTitular().add(nova);
		Pessoa nova2 = new Pessoa();
		nova2.setNome("ana");
		conta.getListaTitular().add(nova2);
		
		Pessoa estranha = new Pessoa();
		estranha.setNome("estranho");
		conta2.getListaTitular().add(estranha);
		
		Agencia ag = new Agencia();
		ag.setCdAgencia(12);
		ag.setCdBanco(22);
		conta.setAgencia(ag);

		Agencia ag2 = new Agencia();
		ag2.setCdAgencia(13);
		ag2.setCdBanco(22);
		conta2.setAgencia(ag2);
		
		Movimentacao movimentacao = new Movimentacao();
		movimentacao.setData(new Date());
		movimentacao.setHora(new Date());
		movimentacao.setDescricao("mov01");
		movimentacao.setValor(BigDecimal.valueOf(10.5f));
		movimentacao.setTipo(TipoMovimentacao.ENTRADA);
		
		Movimentacao movimentacao2 = new Movimentacao();
		movimentacao2.setData(new Date());
		movimentacao2.setHora(new Date());
		movimentacao2.setDescricao("mov02");
		movimentacao2.setValor(BigDecimal.valueOf(10.5f));
		movimentacao2.setTipo(TipoMovimentacao.ENTRADA);
		
		
		EntityManager manager = new JPAUtil().getEntityManager();

		/*Query query = nativa(manager);
		
		List<Movimentacao> lista = query.getResultList();

		Query query2 = jpql(manager);

		List<Conta> contas = query2.getResultList();
		
		
		Query query3 = jpqlMovs(manager);
		
		List<Movimentacao> lista3 = query3.getResultList();
		
		System.out.println(lista3);
		
		
		Query query4 = named(manager);
		
		List<Movimentacao> lista4 = query4.getResultList();
		
		System.out.println(lista4);*/
		
		
		Query query5 = projeto(manager);
		List<Pessoa> lista5 = query5.getResultList();
		
		for (Iterator<Pessoa> iterator = lista5.iterator(); iterator.hasNext();) {
			Pessoa pessoa = (Pessoa) iterator.next();
			System.out.println("\n Titular: "+pessoa.getNome());
			List<Conta> contas = pessoa.getContas();
			for (Iterator<Conta> it = contas.iterator(); it.hasNext();) {
				Conta contai = (Conta) it.next();
				System.out.println("\t Conta N.: "+contai.getNumero());
				List<Movimentacao> movimentacoes = contai.getMovimentacoes();
				TipoMovimentacao anterior = null;
				for (Iterator<Movimentacao> iterator4 = movimentacoes.iterator(); iterator4.hasNext();) 
				{
					Movimentacao movimentacao3 = (Movimentacao) iterator4.next();
					if(anterior!=movimentacao3.getTipo()){
						Query query3 = projetoSoma(manager, pessoa.getId_p());
						List<BigDecimal> teste = query3.getResultList();
						String tipoDesc = (movimentacao3.getTipo().equals(TipoMovimentacao.ENTRADA))?"\n\tENTRADAS - Total ("+teste.get(0)+")": "\n\tSAÍDAS - Total ("+teste.get(1)+")";
						System.out.println(tipoDesc);
						anterior = movimentacao3.getTipo();
					}
					SimpleDateFormat sf = new SimpleDateFormat("dd/MM/YYYY");
					
					System.out.println("\t"+sf.format(movimentacao3.getData())+" - "+movimentacao3.getValor());
				}
			}
		}
		
		/*manager.getTransaction().begin();
		manager.persist(nova);
		manager.persist(nova2);
		manager.persist(estranha);
		
		manager.persist(ag);
		manager.persist(ag2);

		manager.persist(conta);
		manager.persist(conta2);

		manager.persist(movimentacao);
		manager.persist(movimentacao2);
	
		conta.getMovimentacoes().add(movimentacao);
		conta.getMovimentacoes().add(movimentacao2);
		
		System.out.println(conta.getMovimentacoes());
		manager.getTransaction().commit();*/
		
		//Conta contaBanco = manager.find(Conta.class,1);
	
		manager.close();
		

		double fim = System.currentTimeMillis();

	}

	private static Query nativa(EntityManager manager) {
		StringBuilder sqlNativo = new StringBuilder();
		sqlNativo.append(" SELECT * ");
		sqlNativo.append(" FROM Movimentacao m JOIN Conta c ON(c.id_c = m.idConta) ");
		sqlNativo.append(" WHERE c.numero = :numeroConta ");
		
		Query query = manager.createNativeQuery(sqlNativo.toString(),Movimentacao.class);
		query.setParameter("numeroConta",123345);
		return query;
	}
	
	private static Query jpql(EntityManager manager) {
		StringBuilder sqlNativo = new StringBuilder();
		sqlNativo.append(" SELECT c ");
		sqlNativo.append(" FROM Conta c ");
		
		Query query = manager.createQuery(sqlNativo.toString(),Conta.class);
		return query;
	}
	
	private static Query jpqlMovs(EntityManager manager) {
		StringBuilder sqlNativo = new StringBuilder();
		sqlNativo.append(" SELECT c.movimentacoes ");
		sqlNativo.append(" FROM Conta c ");
		sqlNativo.append(" WHERE c.numero = :numeroConta ");
		
		Query query = manager.createQuery(sqlNativo.toString());
		query.setParameter("numeroConta","123345");
		return query;
	}
	
	
	private static Query named(EntityManager manager) {
		StringBuilder sqlNativo = new StringBuilder();
		sqlNativo.append("Conta.movimentacoes");
		
		Query query = manager.createNamedQuery(sqlNativo.toString());
		query.setParameter("numeroConta","123345");
		return query;
	}
	
	
	
	private static Query projeto(EntityManager manager) {
		StringBuilder sqlNativo = new StringBuilder();
		
		sqlNativo.append(" SELECT p ");
		sqlNativo.append(" FROM Pessoa p ");
		sqlNativo.append("  JOIN p.contas as c  ");
		sqlNativo.append("  JOIN c.movimentacoes m  ");
		sqlNativo.append("  GROUP BY p.id ");
		sqlNativo.append("	ORDER BY p.nome, m.tipo, m.data, m.hora  ");
		
		
		Query query = manager.createQuery(sqlNativo.toString());
		
		return query;
	}
	
	private static Query projetoSoma(EntityManager manager, int idPessoa) {
		StringBuilder sqlNativo = new StringBuilder();
		
		sqlNativo.append(" SELECT sum(m.valor) ");
		sqlNativo.append(" FROM Pessoa p ");
		sqlNativo.append("  JOIN p.contas as c  ");
		sqlNativo.append("  JOIN c.movimentacoes m  ");
		sqlNativo.append("  WHERE p.id_p=:idPessoa  ");
		sqlNativo.append("  GROUP BY p.id, m.tipo ");
		
		Query query = manager.createQuery(sqlNativo.toString());
		query.setParameter("idPessoa",idPessoa);
		
		return query;
	}
}
