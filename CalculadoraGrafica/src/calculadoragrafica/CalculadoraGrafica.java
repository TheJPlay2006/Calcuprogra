/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package calculadoragrafica;

/**
 *
 * @author thejplay2006
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
/**
 *
 * @author Jairo
 */

public class CalculadoraGrafica extends javax.swing.JFrame implements ActionListener, KeyListener {

    // Variables para la calculadora
    private JTextField pantalla;
    private JButton[] botonesNumeros;
    private JButton[] botonesOperaciones;
    private JButton[] botonesMemoria;
    private JButton botonIgual, botonPunto, botonCE, botonC, botonPorcentaje, botonMod;
    private JButton botonMasMenos;
    
    // Variables para cálculos
    private double numeroActual = 0;
    private double numeroAnterior = 0;
    private String operacionActual = "";
    private boolean nuevaOperacion = true;
    private double memoria = 0;
    
    // Constantes
    private static final int MAX_DIGITOS = 10;
    private DecimalFormat formatoDecimal = new DecimalFormat("#.##########");
    
    /**
     * Creates new form CalculadoraGrafica
     */
    public CalculadoraGrafica() {
        initComponents();
        inicializarCalculadora();
        configurarEventos();
    }

    private void inicializarCalculadora() {
        // Configurar pantalla
        pantalla = new JTextField("0");
        pantalla.setFont(new Font("Arial", Font.BOLD, 20));
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setEditable(false);
        pantalla.setBackground(Color.WHITE);
        pantalla.setBorder(BorderFactory.createLoweredBevelBorder());
        
        // Inicializar botones numéricos
        botonesNumeros = new JButton[10];
        for (int i = 0; i < 10; i++) {
            botonesNumeros[i] = new JButton(String.valueOf(i));
            botonesNumeros[i].setFont(new Font("Arial", Font.BOLD, 16));
            botonesNumeros[i].addActionListener(this);
            botonesNumeros[i].setBackground(Color.WHITE);
        }
        
        // Inicializar botones de operaciones
        String[] operaciones = {"+", "-", "*", "/"};
        botonesOperaciones = new JButton[4];
        for (int i = 0; i < 4; i++) {
            botonesOperaciones[i] = new JButton(operaciones[i]);
            botonesOperaciones[i].setFont(new Font("Arial", Font.BOLD, 16));
            botonesOperaciones[i].addActionListener(this);
            botonesOperaciones[i].setBackground(new Color(240, 240, 240));
        }
        
        // Inicializar botones de memoria
        String[] memoria = {"MC", "MR", "MS", "M+", "M-"};
        botonesMemoria = new JButton[5];
        for (int i = 0; i < 5; i++) {
            botonesMemoria[i] = new JButton(memoria[i]);
            botonesMemoria[i].setFont(new Font("Arial", Font.BOLD, 11));
            botonesMemoria[i].addActionListener(this);
            botonesMemoria[i].setBackground(new Color(255, 255, 200));
        }
        
        // Inicializar otros botones
        botonIgual = new JButton("=");
        botonPunto = new JButton(".");
        botonCE = new JButton("CE");
        botonC = new JButton("C");
        botonPorcentaje = new JButton("%");
        botonMod = new JButton("MOD");
        botonMasMenos = new JButton("±");
        
        // Configurar fuente y colores para otros botones
        JButton[] otrosBotones = {botonIgual, botonPunto, botonCE, botonC, 
                                 botonPorcentaje, botonMod, botonMasMenos};
        for (JButton boton : otrosBotones) {
            boton.setFont(new Font("Arial", Font.BOLD, 14));
            boton.addActionListener(this);
        }
        
        // Colores especiales
        botonIgual.setBackground(new Color(200, 255, 200));
        botonCE.setBackground(new Color(255, 200, 200));
        botonC.setBackground(new Color(255, 200, 200));
        
        // Agregar componentes al JFrame
        agregarComponentes();
    }
    
    /**
     * Agrega los componentes al layout del JFrame
     */
    private void agregarComponentes() {
        // Cambiar el layout del JFrame
        setLayout(new BorderLayout());
        
        // Panel superior con pantalla
        JPanel panelPantalla = new JPanel(new BorderLayout());
        panelPantalla.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        panelPantalla.add(pantalla, BorderLayout.CENTER);
        
        // Panel de memoria
        JPanel panelMemoria = new JPanel(new GridLayout(1, 5, 2, 2));
        panelMemoria.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        for (JButton boton : botonesMemoria) {
            panelMemoria.add(boton);
        }
        
        // Panel principal de botones
        JPanel panelBotones = new JPanel(new GridLayout(5, 4, 3, 3));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        // Primera fila: CE, C, MOD, /
        panelBotones.add(botonCE);
        panelBotones.add(botonC);
        panelBotones.add(botonMod);
        panelBotones.add(botonesOperaciones[3]); // División
        
        // Segunda fila: 7, 8, 9, *
        panelBotones.add(botonesNumeros[7]);
        panelBotones.add(botonesNumeros[8]);
        panelBotones.add(botonesNumeros[9]);
        panelBotones.add(botonesOperaciones[2]); // Multiplicación
        
        // Tercera fila: 4, 5, 6, -
        panelBotones.add(botonesNumeros[4]);
        panelBotones.add(botonesNumeros[5]);
        panelBotones.add(botonesNumeros[6]);
        panelBotones.add(botonesOperaciones[1]); // Resta
        
        // Cuarta fila: 1, 2, 3, +
        panelBotones.add(botonesNumeros[1]);
        panelBotones.add(botonesNumeros[2]);
        panelBotones.add(botonesNumeros[3]);
        panelBotones.add(botonesOperaciones[0]); // Suma
        
        // Quinta fila: ±, 0, ., %, =
        panelBotones.add(botonMasMenos);
        panelBotones.add(botonesNumeros[0]);
        panelBotones.add(botonPunto);
        panelBotones.add(botonPorcentaje);
        panelBotones.add(botonIgual);
        
        // Agregar paneles al JFrame
        add(panelPantalla, BorderLayout.NORTH);
        add(panelMemoria, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        // Configurar ventana
        setTitle("Calculadora Gráfica - UTN");
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    /**
     * Configura los eventos de teclado
     */
    private void configurarEventos() {
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
    }
    
    /**
     * Maneja los eventos de los botones
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        // Números
        if (comando.matches("[0-9]")) {
            manejarNumero(comando);
        }
        // Operaciones básicas
        else if (comando.matches("[+\\-*/]")) {
            manejarOperacion(comando);
        }
        // Botones especiales
        else {
            switch (comando) {
                case "=":
                    calcularResultado();
                    break;
                case ".":
                    agregarPunto();
                    break;
                case "CE":
                    limpiarEntrada();
                    break;
                case "C":
                    limpiarTodo();
                    break;
                case "%":
                    calcularPorcentaje();
                    break;
                case "MOD":
                    manejarOperacion("MOD");
                    break;
                case "±":
                    cambiarSigno();
                    break;
                case "MC":
                    limpiarMemoria();
                    break;
                case "MR":
                    recuperarMemoria();
                    break;
                case "MS":
                    guardarMemoria();
                    break;
                case "M+":
                    sumarMemoria();
                    break;
                case "M-":
                    restarMemoria();
                    break;
            }
        }
    }
    

    private void manejarNumero(String numero) {
        String textoActual = pantalla.getText();
        
        if (nuevaOperacion || textoActual.equals("0")) {
            pantalla.setText(numero);
            nuevaOperacion = false;
        } else {
            if (textoActual.length() < MAX_DIGITOS) {
                pantalla.setText(textoActual + numero);
            }
        }
    }

    private void manejarOperacion(String operacion) {
        if (!operacionActual.isEmpty() && !nuevaOperacion) {
            calcularResultado();
        }
        
        numeroAnterior = Double.parseDouble(pantalla.getText());
        operacionActual = operacion;
        nuevaOperacion = true;
    }
    
    /**
     * Calcula el resultado de la operación actual
     */
    private void calcularResultado() {
        if (operacionActual.isEmpty()) return;
        
        numeroActual = Double.parseDouble(pantalla.getText());
        double resultado = 0;
        
        try {
            switch (operacionActual) {
                case "+":
                    resultado = numeroAnterior + numeroActual;
                    break;
                case "-":
                    resultado = numeroAnterior - numeroActual;
                    break;
                case "*":
                    resultado = numeroAnterior * numeroActual;
                    break;
                case "/":
                    if (numeroActual != 0) {
                        resultado = numeroAnterior / numeroActual;
                    } else {
                        mostrarError("Error: División por cero");
                        return;
                    }
                    break;
                case "MOD":
                    if (numeroActual != 0) {
                        resultado = numeroAnterior % numeroActual;
                    } else {
                        mostrarError("Error: División por cero");
                        return;
                    }
                    break;
            }
            
            pantalla.setText(formatoDecimal.format(resultado));
            operacionActual = "";
            nuevaOperacion = true;
            
        } catch (Exception ex) {
            mostrarError("Error en cálculo");
        }
    }
    
    private void agregarPunto() {
        String texto = pantalla.getText();
        if (!texto.contains(".") && texto.length() < MAX_DIGITOS) {
            if (nuevaOperacion) {
                pantalla.setText("0.");
                nuevaOperacion = false;
            } else {
                pantalla.setText(texto + ".");
            }
        }
    }
    
    private void limpiarEntrada() {
        pantalla.setText("0");
        nuevaOperacion = true;
    }
    

    private void limpiarTodo() {
        pantalla.setText("0");
        numeroActual = 0;
        numeroAnterior = 0;
        operacionActual = "";
        nuevaOperacion = true;
    }
    
    private void calcularPorcentaje() {
        double valor = Double.parseDouble(pantalla.getText());
        double resultado = valor / 100;
        pantalla.setText(formatoDecimal.format(resultado));
        nuevaOperacion = true;
    }
    

    private void cambiarSigno() {
        double valor = Double.parseDouble(pantalla.getText());
        valor = -valor;
        pantalla.setText(formatoDecimal.format(valor));
    }
    
    private void limpiarMemoria() {
        memoria = 0;
    }
    

    private void recuperarMemoria() {
        pantalla.setText(formatoDecimal.format(memoria));
        nuevaOperacion = true;
    }
    
    private void guardarMemoria() {
        memoria = Double.parseDouble(pantalla.getText());
    }

    private void sumarMemoria() {
        memoria += Double.parseDouble(pantalla.getText());
    }
    

    private void restarMemoria() {
        memoria -= Double.parseDouble(pantalla.getText());
    }
    

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        limpiarTodo();
    }
    
    // Implementación de KeyListener para accesos directos
    
    @Override
    public void keyPressed(KeyEvent e) {
        int codigo = e.getKeyCode();
        
        // Números
        if (codigo >= KeyEvent.VK_0 && codigo <= KeyEvent.VK_9) {
            manejarNumero(String.valueOf(codigo - KeyEvent.VK_0));
        }
        // Números del teclado numérico
        else if (codigo >= KeyEvent.VK_NUMPAD0 && codigo <= KeyEvent.VK_NUMPAD9) {
            manejarNumero(String.valueOf(codigo - KeyEvent.VK_NUMPAD0));
        }
        // Operaciones
        else {
            switch (codigo) {
                case KeyEvent.VK_PLUS:
                case KeyEvent.VK_ADD:
                    manejarOperacion("+");
                    break;
                case KeyEvent.VK_MINUS:
                case KeyEvent.VK_SUBTRACT:
                    manejarOperacion("-");
                    break;
                case KeyEvent.VK_MULTIPLY:
                    manejarOperacion("*");
                    break;
                case KeyEvent.VK_DIVIDE:
                case KeyEvent.VK_SLASH:
                    manejarOperacion("/");
                    break;
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_EQUALS:
                    calcularResultado();
                    break;
                case KeyEvent.VK_PERIOD:
                case KeyEvent.VK_DECIMAL:
                    agregarPunto();
                    break;
                case KeyEvent.VK_DELETE:
                    limpiarEntrada();
                    break;
                case KeyEvent.VK_ESCAPE:
                    limpiarTodo();
                    break;
            }
        }
        
        // Accesos directos con CTRL
        if (e.isControlDown()) {
            switch (codigo) {
                case KeyEvent.VK_L:
                    limpiarMemoria();
                    break;
                case KeyEvent.VK_R:
                    recuperarMemoria();
                    break;
                case KeyEvent.VK_M:
                    guardarMemoria();
                    break;
                case KeyEvent.VK_P:
                    sumarMemoria();
                    break;
                case KeyEvent.VK_Q:
                    restarMemoria();
                    break;
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // No implementado
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // No implementado
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // </editor-fold>



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CalculadoraGrafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalculadoraGrafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalculadoraGrafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalculadoraGrafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CalculadoraGrafica().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
