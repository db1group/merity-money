package br.com.db1.meritmoney.service.mapper;

import br.com.db1.meritmoney.domain.Equipe;
import br.com.db1.meritmoney.service.dto.EquipeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface EquipeMapper extends EntityMapper<EquipeDto, Equipe> {

    default Equipe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Equipe equipe = new Equipe();
        equipe.setId(id);
        return equipe;
    }

}
