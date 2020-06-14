package br.com.db1.meritmoney.service.mapper;

import br.com.db1.meritmoney.domain.Transacao;
import br.com.db1.meritmoney.service.dto.TransacaoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TransacaoMapper extends EntityMapper<TransacaoDto, Transacao> {

}
