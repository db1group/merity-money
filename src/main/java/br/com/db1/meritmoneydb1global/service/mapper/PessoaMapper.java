package br.com.db1.meritmoneydb1global.service.mapper;

import br.com.db1.meritmoneydb1global.domain.Pessoa;
import br.com.db1.meritmoneydb1global.service.dto.PessoaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface PessoaMapper extends EntityMapper<PessoaDto, Pessoa> {

    default Pessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        return pessoa;
    }
}