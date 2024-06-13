package com.mysite.rent.Res;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResRepository extends JpaRepository<Res, String> {
	Optional<Res> findByResNumber(String resNumber);

}
