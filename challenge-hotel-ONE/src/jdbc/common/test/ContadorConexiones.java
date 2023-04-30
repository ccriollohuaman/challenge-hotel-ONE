package jdbc.common.test;

public class ContadorConexiones {
	
	private static ContadorConexiones instance = null;
    private int conexion = 0;
    
    public ContadorConexiones() {}
    
    public static ContadorConexiones getInstance() {
        if (instance == null) {
            instance = new ContadorConexiones();
        }
        return instance;
    }
    
    public synchronized void increment() {
    	conexion++;
        System.out.println("Número de conexiones: " + conexion);
    }
    
    public synchronized void decrement() {
    	conexion--;
        System.out.println("Número de conexiones: " + conexion);
    }

}
