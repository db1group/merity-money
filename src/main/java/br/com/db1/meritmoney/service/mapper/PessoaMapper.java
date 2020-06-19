package br.com.db1.meritmoney.service.mapper;

import br.com.db1.meritmoney.domain.Pessoa;
import br.com.db1.meritmoney.service.dto.PessoaDto;
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