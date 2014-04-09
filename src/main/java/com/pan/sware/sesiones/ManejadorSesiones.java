package com.pan.sware.sesiones;


import com.pan.sware.TO.UsuarioTO;
import com.pan.sware.db.ConnectionUtil;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

/**
 *
 * @author estebanfcv
 */
public class ManejadorSesiones {

    private static void eliminarConexiones(String idSession) {
        UsuarioTO usuario = DatosSesion.getInstance().getUsuariosRegistrados().get(idSession);
        ConnectionUtil.endConnection(usuario);
    }

    public static synchronized void agregarSesion(UsuarioTO usuario) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true));
        UserAgentStringParser uasp = UADetectorServiceFactory.getResourceModuleParser();
        ReadableUserAgent agent = uasp.parse(request.getHeader("User-Agent"));
        UsuarioTO user = usuario.clone();
        user.crearConexion();
        user.asignarfechasSesion();
        user.setIdSesion(session.getId());
//        user.setOS(agent.getOperatingSystem().getName());
//        user.setOSVersion(agent.getOperatingSystem().getVersionNumber().toVersionString());
//        user.setBrowser(agent.getName());
//        user.setBrowserVersion(agent.getVersionNumber().toVersionString());
//        user.setIp(request.getRemoteAddr());
//        SoxDAO.insertarSOXAcceso(user, 1, null);
        DatosSesion.getInstance().getUsuariosRegistrados().put(session.getId(), user);
    }

    public static synchronized void eliminarSesion() {
        HttpSession session = ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true));
//        eliminarHilo(session.getId());
//        SoxDAO.insertarSOXAccesoLogout(getUsuario(), 0);//"Se cerro la sesion manualmente");
        eliminarConexiones(session.getId());
        DatosSesion.getInstance().getUsuariosRegistrados().remove(session.getId());
//        FacesContext.getCurrentInstance().getExternalContext().redirect("/Aquila/j_spring_security_logout");
    }

    public static synchronized void eliminarSesion(UsuarioTO usuario) {
//        SoxDAO.insertarSOXAccesoLogout(usuario, 1);//"Se cerro la sesion automaticamente");
        eliminarConexiones(usuario.getIdSesion());
        DatosSesion.getInstance().getUsuariosRegistrados().remove(usuario.getIdSesion());
    }

    public static synchronized UsuarioTO getUsuario() {
        HttpSession session = ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true));
        return DatosSesion.getInstance().getUsuariosRegistrados().get(session.getId());
    }

    public static synchronized Map<String, UsuarioTO> getUsuariosRegistrados() {
        return DatosSesion.getInstance().getUsuariosRegistrados();
    }

    public static synchronized void modificarUbicacion(String ubicacion) {
        HttpSession session = ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true));
        System.out.println("cambio de ubicacion:::" + ubicacion);
        DatosSesion.getInstance().getUsuariosRegistrados().get(session.getId()).setUbicacion(ubicacion);
    }
    public static synchronized void modificarAvatar(byte[] avatar) {
        HttpSession session = ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true));
        DatosSesion.getInstance().getUsuariosRegistrados().get(session.getId()).setAvatar(avatar);
    }
    
    public static synchronized boolean isSesionAbierta(){
        return null != FacesContext.getCurrentInstance();
    }
    
    private ManejadorSesiones(){}
}
