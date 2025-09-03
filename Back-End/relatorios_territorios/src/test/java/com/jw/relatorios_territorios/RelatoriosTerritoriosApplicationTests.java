package com.jw.relatorios_territorios;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RelatoriosTerritoriosApplicationTests {

	@Test
	void contextLoads() {
	}

}


//openssl x509 -req -in kafka.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out kafka.crt -days 365 -extfile san.cnf -extensions v3_req