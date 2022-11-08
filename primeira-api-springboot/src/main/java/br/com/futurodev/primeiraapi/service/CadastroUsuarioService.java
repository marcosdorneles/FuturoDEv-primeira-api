package br.com.futurodev.primeiraapi.service;

import br.com.futurodev.primeiraapi.model.UsuarioModel;
import br.com.futurodev.primeiraapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioModel salvar(UsuarioModel usuario){
        return usuarioRepository.save(usuario);

    }

    public void delete(Long idUsuario){
        usuarioRepository.deleteById(idUsuario);
    }

    //public ResponseEntity<UsuarioModel> getUserById

    public UsuarioModel getUserById(Long idUsuario){
        return usuarioRepository.findById(idUsuario).get();
    }

//    public ResponseEntity<UsuarioModel> getUserByName(@RequestParam(name = "nome")String nome){
//        List<UsuarioModel> usuarios = usuarioRepository.getUserByName(nome);
//        return new ResponseEntity<List<UsuarioModel>>(usuarios, HttpStatus.OK);
//    }

//    public List<UsuarioModel> getUserByName(String nome){
//
//        return usuarioRepository.getUserByName(nome);
//    }

}
