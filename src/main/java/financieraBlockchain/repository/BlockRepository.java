package financieraBlockchain.repository;

import financieraBlockchain.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {}
