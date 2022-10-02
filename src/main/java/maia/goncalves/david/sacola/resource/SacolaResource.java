package maia.goncalves.david.sacola.resource;

import lombok.RequiredArgsConstructor;
import maia.goncalves.david.sacola.model.Item;
import maia.goncalves.david.sacola.model.Sacola;
import maia.goncalves.david.sacola.resource.dto.ItemDto;
import maia.goncalves.david.sacola.service.SacolaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ifood-davidmaia/sacolas")
@RequiredArgsConstructor
public class SacolaResource {

    private final SacolaService sacolaService;

    @PostMapping
    public Item incluirNaSacola(@RequestBody ItemDto itemDto){
        return sacolaService.incluirNaSacola(itemDto);
    }

    @GetMapping("/{id}")
    public Sacola verSacola(@PathVariable("id") Long id){
        return sacolaService.verSacola(id);
    }
    @PatchMapping("/fecharSacola/{sacolaId}")
    public Sacola fecharSacola(@PathVariable("sacolaId") Long sacolaId,
                               @RequestParam("formaPagamento") int formaPagamento){
        return sacolaService.fecharSacola(sacolaId, formaPagamento);
    }

}
