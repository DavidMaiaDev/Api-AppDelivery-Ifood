package maia.goncalves.david.sacola.service;

import maia.goncalves.david.sacola.model.Item;
import maia.goncalves.david.sacola.model.Sacola;
import maia.goncalves.david.sacola.resource.dto.ItemDto;
import org.springframework.stereotype.Repository;

@Repository
public interface SacolaService {
    Item incluirNaSacola(ItemDto itemDto);
    Sacola verSacola(Long id);
    Sacola fecharSacola(Long id, int formaPagamento);

}
