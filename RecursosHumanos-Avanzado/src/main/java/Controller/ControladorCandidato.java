package Controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Model.Candidato;
import Model.Candidato_Tecnologia;
import Repositorios.CandidatosRepositorio;
import Repositorios.RelacionRepositorio;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/candidatos")
public class ControladorCandidato {
	
	@Autowired
	CandidatosRepositorio candidatosRepositorio;
	
	@Autowired
	RelacionRepositorio repositorioRelacion;
	
	@PostMapping(path="/add")
	@CrossOrigin
	public @ResponseBody Object anadirCandidato(@RequestBody Candidato c) {
		
		c.setID(null);
		
		return candidatosRepositorio.save(c);
	}
	
	@GetMapping(path="/ver")
	@CrossOrigin
	public @ResponseBody Iterable<Candidato> getAllCandidatos() throws JRException {
		// This returns a JSON or XML with the candidatos 
		return candidatosRepositorio.findAll();
	}
	
	@DeleteMapping(path="/borrar")
	@CrossOrigin
	public @ResponseBody String borrar(@RequestParam Integer id) {
		
		if(!candidatosRepositorio.existsById(id)) {
			return "No se ha encontrado canidato";
			}
			Iterable<Candidato_Tecnologia>relacion = repositorioRelacion.findAll();
			Iterator<Candidato_Tecnologia> iteratorLista = relacion.iterator();
			boolean hasRelation = false;
			while(iteratorLista.hasNext()) {
				Candidato_Tecnologia c = iteratorLista.next();
				if(c.getID_CANDIDATO().getID() == id ) {
					hasRelation=true;
					
				}
			}
			if(!hasRelation) {
				candidatosRepositorio.deleteById(id);
				return "Se ha eliminado un candidato";
			}
			return"No se puede eliminar este registro";
	}
	
	@PutMapping(path="/actualizar")
	@CrossOrigin
	public @ResponseBody String actualizar(@RequestBody Candidato c) {
		Integer id = c.getID();
		if(candidatosRepositorio.findById(id).get() != null) {
			candidatosRepositorio.save(c);
			return "Actualizado";

		}
		return "Error";
	}
	
	/*public void reporte() throws JRException {
		JasperReport reporte;
		String path = "C:\\Users\\";
		
		reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
		JasperPrint jprint = JasperFillManager.fillReport(reporte, null);
		JasperViewer viewer = new JasperViewer(jprint, false);
		viewer.setVisible(true);
	}*/

	
}