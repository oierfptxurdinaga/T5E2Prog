HASIERA
	Hasieratu aldagaiak
	i  <- 0
	BITARTEAN i < denboraldiko jornada kopurua EGIN
		j <- 0
		BITARTEAN i < jornadako partidu kopurua EGIN
			BALDIN etxeko taldea egokia da && kanpoko taldea egokia da ORDUAN
				Gorde etxeko eta kanpoko golak
				ITZULI
			AMAITU BALDIN
			j <- j + 1
		AMAITU BITARTEAN
		i <- i + 1
	AMAITU BITARTEAN
AMAIERA