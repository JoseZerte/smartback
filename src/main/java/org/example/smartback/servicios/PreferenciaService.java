package org.example.smartback.servicios;

import org.example.smartback.model.Preferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.smartback.repository.PreferenciaRepository;

import java.util.List;

@Service
public class PreferenciaService {

    @Autowired
    private PreferenciaRepository preferenciaRepository;

    public Preferencia guardar(Preferencia preferencia) {
        return preferenciaRepository.save(preferencia);
    }

    public List<Preferencia> listar() {
        return preferenciaRepository.findAll();
    }

    public void eliminar(int id) {
        preferenciaRepository.deleteById(id);
    }
}
