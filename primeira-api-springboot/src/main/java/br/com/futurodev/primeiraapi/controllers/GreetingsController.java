package br.com.futurodev.primeiraapi.controllers;

import br.com.futurodev.primeiraapi.model.ProdutoModel;
import br.com.futurodev.primeiraapi.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

    @Autowired // IC -> injeção de dependências
   private ProdutoRepository produtoRepository;

    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Hello " + name + "!";
    }

//    @GetMapping(value = "mostrarnome/{nome}")
//    @ResponseStatus(HttpStatus.OK)
//    public String mostrarnome(@PathVariable String nome) {
//        return "Olá" + nome;
//    }

    @RequestMapping(value = "/produto/{descricao}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String salvar(@PathVariable String descricao){
        ProdutoModel produto = new ProdutoModel();
        produto.setDescricao(descricao);
        produtoRepository.save(produto); // -> salva no banco de dados um produto
        return "Produto " + descricao + " registrado com sucesso!";
    }

    @GetMapping(value = "/produtos")
    @ResponseBody // retorna os dados no corpo da resposta
    public ResponseEntity  <List<ProdutoModel>> listarProdutos(){
        List <ProdutoModel> produtos = produtoRepository.findAll(); // consulta no banco de dados todos os produtos

        return new ResponseEntity<List<ProdutoModel>>(produtos, HttpStatus.OK); //-> Retorna a resposta em JSON
    }

    @PostMapping(value = "/produto/salvar") //Mapeia a URL
    @ResponseBody //descreve a resposta, informando que o retorno será no corpo da requisição
    public ResponseEntity<ProdutoModel> salvar(@RequestBody ProdutoModel produto){ //recebe os dados para salvar

       ProdutoModel prod =  produtoRepository.save(produto);
       return new ResponseEntity<ProdutoModel>(prod, HttpStatus.CREATED);


    }

    @DeleteMapping(value = "/produto/delete") //Mapeia a URL
    @ResponseBody // descrição da resposta
    public ResponseEntity<String>delete(@RequestParam long idProduto){ // Recebe da requisição o parâmetro

        produtoRepository.deleteById(idProduto);
      return new ResponseEntity<String>("Produto deletado com sucesso!", HttpStatus.OK);

    }

}


