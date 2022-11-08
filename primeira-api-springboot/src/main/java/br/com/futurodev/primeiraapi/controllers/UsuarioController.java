package br.com.futurodev.primeiraapi.controllers;


import br.com.futurodev.primeiraapi.dto.TelefoneRepresentationModel;
import br.com.futurodev.primeiraapi.dto.UsuarioRepresentationModel;
import br.com.futurodev.primeiraapi.input.UsuarioInput;
import br.com.futurodev.primeiraapi.model.UsuarioModel;
import br.com.futurodev.primeiraapi.repository.UsuarioRepository;
import br.com.futurodev.primeiraapi.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping (value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

//    @PostMapping(value = "/", produces = "aplication/json")
//    public ResponseEntity<UsuarioModel> cadastrar(@RequestBody UsuarioModel usuario){
//        UsuarioModel usu = usuarioRepository.save(usuario);
//        return new ResponseEntity<UsuarioModel>(usu, HttpStatus.CREATED);
//    }

//    @PostMapping(value = "/", produces = "application/json")
//    public ResponseEntity<UsuarioRepresentationModel> cadastrar(@RequestBody UsuarioModel usuario){
//        UsuarioModel usu = cadastroUsuarioService.salvar(usuario);
//        return new ResponseEntity<UsuarioRepresentationModel>(toModel(usu), HttpStatus.CREATED);
//    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<UsuarioRepresentationModel> cadastrar(@RequestBody UsuarioInput usuarioInput){
        //Converte usuarioInput em usuarioModel
        UsuarioModel usu = toDomainObject(usuarioInput);
        //Service para salvar no banco de dados
                cadastroUsuarioService.salvar(usu);
        //retorno com a resposta da requisição e converte UsuarioModel em Representação
        return new ResponseEntity<UsuarioRepresentationModel>(toModel(usu), HttpStatus.CREATED);
    }


//    @PutMapping(value = "/", produces = "applicaition/json")
//    public ResponseEntity<UsuarioRepresentationModel> atualizar(@RequestBody UsuarioModel usuario){
//        UsuarioModel usu = cadastroUsuarioService.salvar(usuario);
//        return new ResponseEntity<UsuarioRepresentationModel>(toModel(usu), HttpStatus.OK);
//    }

    @PutMapping(value = "/", produces = "applicaition/json")
    public ResponseEntity<UsuarioRepresentationModel> atualizar(@RequestBody UsuarioInput usuarioInput){
        UsuarioModel usu = cadastroUsuarioService.salvar(toDomainObject(usuarioInput));
        return new ResponseEntity<UsuarioRepresentationModel>(toModel(usu), HttpStatus.OK);
    }

    @DeleteMapping(value = "/")
    @ResponseBody
    public ResponseEntity<String>delete(@RequestParam Long idUsuario){
        cadastroUsuarioService.delete(idUsuario);
        return new ResponseEntity<String>("Usuário deletado com sucesso", HttpStatus.OK);
    }

//    @GetMapping(value = "/{idUsuario}", produces = "application/json")
//    public ResponseEntity<UsuarioModel> getUserById(@PathVariable(value = "idUsuario")Long idUsuario){
//        UsuarioModel usu = cadastroUsuarioService.getUserById(idUsuario);
//        return new ResponseEntity<UsuarioModel>(usu, HttpStatus.OK);
//    }


    @GetMapping(value = "/{idUsuario}", produces = "application/json")
    public ResponseEntity<UsuarioRepresentationModel> getUserById(@PathVariable(value = "idUsuario")Long idUsuario){
        UsuarioModel usu = cadastroUsuarioService.getUserById(idUsuario);
        UsuarioRepresentationModel usuarioRepresentationModel = toModel(usu);
        //return new ResponseEntity<UsuarioRepresentationModel>(usu, HttpStatus.OK);
        return new ResponseEntity<UsuarioRepresentationModel>(usuarioRepresentationModel, HttpStatus.OK);
    }




    //No Crud repository não tem essa função de getUserByName

//    @GetMapping(value ="/buscaPorNome", produces="application/json")
//    @ResponseBody
//    public ResponseEntity<UsuarioModel> getUserByName(@RequestParam(name = "nome")String nome){
//        List<UsuarioModel> usuarios = usuarioRepository.getUserByName(nome);
//        return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);
//    }

    private  UsuarioRepresentationModel toModel(UsuarioModel usu) {
        UsuarioRepresentationModel usuarioRepresentationModel = new UsuarioRepresentationModel(); //conversão da entidade UsuarioModel para URM
        usuarioRepresentationModel.setId(usu.getId());
        usuarioRepresentationModel.setLogin(usu.getLogin());
        usuarioRepresentationModel.setNome(usu.getNome());

       // List<TelefoneRepresentationModel> telefonesRepresentationModel = new ArrayList<TelefoneRepresentationModel>();

        for (int i = 0; i<usu.getTelefones().size();i++){
            TelefoneRepresentationModel telefonesRepresentationModel = new TelefoneRepresentationModel();
            telefonesRepresentationModel.get(i).setId(usu.getTelefones().get(i).getId());
            telefonesRepresentationModel.get(i).setNumero(usu.getTelefones().get(i).getNumero());
            telefonesRepresentationModel.get(i).setNumero(usu.getTelefones().get(i).getTipo());

            usuarioRepresentationModel.setTelefones(telefonesRepresentationModel);
        }
        telefoneRepresentationModel.setId(usu.getTelefones().);
        usuarioRepresentationModel.setTelefones(usu.getTelefones());
        return usuarioRepresentationModel;
    }


    private List<UsuarioRepresentationModel>toCollectionModel(List<UsuarioModel>usuarosModel){
        return usuarosModel.stream()
                .map(usuarioModel -> toModel(usuarioModel))
                .collect(Collectors.toList());
    }

    // converte um objeto do tipo UsuarioInput para UsuarioModel
    private UsuarioModel toDomainObject(UsuarioInput usuarioInput){

        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setId(usuarioInput.getId());
        usuarioModel.setLogin(usuarioInput.getLogin());
        usuarioModel.setNome(usuarioInput.getNome());
        usuarioModel.setSenha(usuarioInput.getSenha());

        return usuarioModel;
    }
}
