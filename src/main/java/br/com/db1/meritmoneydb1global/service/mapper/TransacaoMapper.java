package br.com.db1.meritmoneydb1global.service.mapper;

import br.com.db1.meritmoneydb1global.domain.Transacao;
import br.com.db1.meritmoneydb1global.service.dto.TransacaoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TransacaoMapper extends EntityMapper<TransacaoDto, Transacao> {

}
