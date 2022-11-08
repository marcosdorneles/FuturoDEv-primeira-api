package br.com.futurodev.primeiraapi.repository;


import br.com.futurodev.primeiraapi.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



// essa interface imprementa um repositório do jpa com algumas funções pré-definidas
@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
}
