package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, UUID> {

    @Query(value = "select * from notificacao where id_publicador_emissor is null order by created_at desc", nativeQuery = true)
    public List<Notificacao> findAllCommon();

    @Query(value = "select * from notificacao where id_publicador_remetente = :id and id_publicador_emissor <> :id order by created_at desc", nativeQuery = true)
    public List<Notificacao> findAllPersonal(@Param("id") UUID id);

    @Modifying
    @Query(value = "update notificacao set lida = true where id in :ids", nativeQuery = true)
    public void marcarComoLidas(@Param("ids") List<UUID> ids);
}
