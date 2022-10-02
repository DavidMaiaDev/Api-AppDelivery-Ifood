package maia.goncalves.david.sacola.service.impl;

import lombok.RequiredArgsConstructor;
import maia.goncalves.david.sacola.enumeration.FormaDePagamento;
import maia.goncalves.david.sacola.model.Item;
import maia.goncalves.david.sacola.model.Restaurante;
import maia.goncalves.david.sacola.model.Sacola;
import maia.goncalves.david.sacola.repository.ItemRepository;
import maia.goncalves.david.sacola.repository.ProdutoRepository;
import maia.goncalves.david.sacola.repository.SacolaRepository;
import maia.goncalves.david.sacola.resource.dto.ItemDto;
import maia.goncalves.david.sacola.service.SacolaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {

    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemRepository itemRepository;

    @Override
    public Item incluirNaSacola(ItemDto itemDto) {
        Sacola sacola = verSacola(itemDto.getSacolaId());
        if (sacola.isFechada()) {
            throw new RuntimeException("Esta Sacola está fechada.");
        }

        Item itemParaSerInserido = Item.builder()
                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtoRepository.findById(itemDto.getProdutoId()).orElseThrow(
                        () -> {
                            throw new RuntimeException("Essa produto não existe!");
                        }
                ))
                .build();

        List<Item> itensDaSacola = sacola.getItens();
        if (itensDaSacola.isEmpty()) {
            itensDaSacola.add(itemParaSerInserido);
        } else {
            Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante();
            Restaurante restauranteDoItemParaAdicionar = itemParaSerInserido.getProduto().getRestaurante();

            if (restauranteAtual.equals(restauranteDoItemParaAdicionar)) {
                itensDaSacola.add(itemParaSerInserido);
            } else {
                throw new RuntimeException("Não é possível adicionar produtos de restaurantes diferentes. Feche a sacola ou esvazie.");
            }
        }

        List<Double> valorDosItens = new ArrayList<>();
        for(Item itemDaSacola : itensDaSacola){
           double valorTotalItem =
                   itemDaSacola.getProduto().getValorUnitario() * itemDaSacola.getQuantidade();
            valorDosItens.add(valorTotalItem);
        }

        double valorTotalSacola = valorDosItens.stream()
                        .mapToDouble(valorTotalDeCadaItem -> valorTotalDeCadaItem)
                                .sum();
        sacola.setValorTotal(valorTotalSacola);
        sacolaRepository.save(sacola);
        return itemParaSerInserido;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Essa sacola não existe!");
                }

        );
    }

    @Override
    public Sacola fecharSacola(Long id, int numeroformaPagamento) {
        Sacola sacola = verSacola(id);
        if (sacola.getItens().isEmpty()) {
            throw new RuntimeException("Inclua Itens na Sacola!");
        }

        FormaDePagamento formaPagamento = numeroformaPagamento == 0 ? FormaDePagamento.DINHEIRO
                : FormaDePagamento.MAQUINETA;

        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);


    }
}
