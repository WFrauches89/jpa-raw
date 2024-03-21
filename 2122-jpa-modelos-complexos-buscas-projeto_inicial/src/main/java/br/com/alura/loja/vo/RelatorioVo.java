package br.com.alura.loja.vo;

import java.time.LocalDate;

public class RelatorioVo {

    private String nomeProduto;
    private Long quantidadeVenda;
    private LocalDate dataUltimaVenda;

    public RelatorioVo(String nomeProduto, Long quantidadeVenda, LocalDate dataUltimaVenda) {
        this.nomeProduto = nomeProduto;
        this.quantidadeVenda = quantidadeVenda;
        this.dataUltimaVenda = dataUltimaVenda;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public Long getQuantidadeVenda() {
        return quantidadeVenda;
    }

    public LocalDate getDataUltimaVenda() {
        return dataUltimaVenda;
    }

    @Override
    public String toString() {
        return "RelatorioVo{" +
                "nomeProduto='" + nomeProduto + '\'' +
                ", quantidadeVenda=" + quantidadeVenda +
                ", dataUltimaVenda=" + dataUltimaVenda +
                '}';
    }
}
