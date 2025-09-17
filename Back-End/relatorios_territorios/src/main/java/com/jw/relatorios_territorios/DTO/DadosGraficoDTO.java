package com.jw.relatorios_territorios.DTO;

import java.util.List;

public record DadosGraficoDTO (GraficoPizzaDTO graficoPizzaDTO, List<GraficoLinhaDTO> graficoLinhaDTOS, String totalHorasMes, String TotalHorasAno){ }
