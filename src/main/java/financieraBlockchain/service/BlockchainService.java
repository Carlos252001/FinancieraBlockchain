package financieraBlockchain.service;

import financieraBlockchain.model.Block;
import financieraBlockchain.repository.BlockRepository;
import financieraBlockchain.util.SHA256Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlockchainService {

    @Autowired
    private BlockRepository blockRepo;

    public Block crearNuevoBloque(String companyName, BigDecimal amount, Double interestRate, LocalDate dueDate) {
        List<Block> todos = blockRepo.findAll();

        String previousHash = todos.isEmpty() ? "0" : todos.get(todos.size() - 1).getCurrentHash();

        Block nuevo = new Block();
        nuevo.setTimestamp(LocalDateTime.now());
        nuevo.setCompanyName(companyName);
        nuevo.setAmount(amount);
        nuevo.setInterestRate(interestRate);
        nuevo.setDueDate(dueDate);
        nuevo.setPreviousHash(previousHash);

        String hashCalculado = SHA256Helper.hash(
            previousHash +
            companyName +
            amount +
            interestRate +
            dueDate +
            nuevo.getTimestamp()
        );
        nuevo.setCurrentHash(hashCalculado);

        return blockRepo.save(nuevo);
    }

    public boolean verificarCadena() {
        List<Block> bloques = blockRepo.findAll();

        for (int i = 1; i < bloques.size(); i++) {
            Block actual = bloques.get(i);
            Block anterior = bloques.get(i - 1);

            String recalculatedHash = SHA256Helper.hash(
                actual.getPreviousHash() +
                actual.getCompanyName() +
                actual.getAmount() +
                actual.getInterestRate() +
                actual.getDueDate() +
                actual.getTimestamp()
            );

            if (!actual.getCurrentHash().equals(recalculatedHash)) {
                return false;
            }

            if (!actual.getPreviousHash().equals(anterior.getCurrentHash())) {
                return false;
            }
        }

        return true;
    }

    public List<Block> listarBloques() {
        return blockRepo.findAll();
    }

    
}
