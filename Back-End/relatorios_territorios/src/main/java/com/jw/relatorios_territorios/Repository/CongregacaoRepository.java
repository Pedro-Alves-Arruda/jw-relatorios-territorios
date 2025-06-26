package com.jw.relatorios_territorios.Repository;

import com.jw.relatorios_territorios.Models.Congregacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CongregacaoRepository extends JpaRepository<Congregacao, Integer> {

    public Optional<Congregacao> findById(@Param("id") Integer id);
}
