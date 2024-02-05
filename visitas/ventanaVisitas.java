/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package visitas;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;
import java.util.prefs.Preferences;
import java.awt.Image;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Month;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gerardo Sánchez Nilo
 */
public class ventanaVisitas extends javax.swing.JFrame {

    DefaultTableModel defecto = new DefaultTableModel();
    private static List<String[]> datos;
    private static DateTimeFormatter dtf, dtf2, formatoDiaS;
    private static LocalDateTime tiempo;
    private static String File_Path = "", File_Dir = "", File_Name = "";
    private static ventanaVisitas ventana;

    //Getters y Setters para las variables privadas
    public static String getFile_Path() {
        return File_Path;
    }

    public static void setFile_Path(String File_Path) {
        ventanaVisitas.File_Path = File_Path;
    }

    public static String getFile_Dir() {
        return File_Dir;
    }

    public static void setFile_Dir(String File_Dir) {
        ventanaVisitas.File_Dir = File_Dir;
    }

    public static String getFile_Name() {
        return File_Name;
    }

    public static void setFile_Name(String File_Name) {
        ventanaVisitas.File_Name = File_Name;
    }
    private final String archivo = "selectedFile", leido = "firstOpen", directorio = "selectedDir", nombre = "SelectedFileN";
    private visitasOrdinarias[] visitas = new visitasOrdinarias[185];
    private static int v_actual = 0;

    /**
     * Creates new form ventanaVisitas
     */
    public ventanaVisitas() {
        Image icon = null;
        try {
            icon = ImageIO.read(getClass().getResource("/visitas/imagenes/icono.png"));
        } catch (IOException ex) {
            Logger.getLogger(ventanaVisitas.class.getName()).log(Level.SEVERE, null, ex);
        }

        tiempo = LocalDateTime.now();

        this.setTitle("Visitas ordinarias de la virgen de Zapopan " + tiempo.getYear());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(icon);

        formatoDiaS = DateTimeFormatter.ofPattern("E, d/MMM/yyyy h:mm a");

        initComponents();
        //Mandamos llamar a las funciones necesarias para que el programa inicie
        leerPreferencias();
        abrir();
        calculaTiempo();
        calculaProxima();

    }
//Funcion que inicializa el arreglo de visitas

    public void inicializarVisitas() {
        for (int x = 0; x < visitas.length; x++) {
            visitas[x] = new visitasOrdinarias();
        }

    }

    public void leerPreferencias() {
        Preferences prefs = Preferences.userNodeForPackage(ventanaVisitas.class);

        //Si no hay un archivo almacenado en preferencias, pedir al usuario un archivo
        if (prefs.getBoolean(leido, false) == false) {
            selectFile();
        } else {
            //En caso contrario recuperar el archivo de las preferencias
            File_Path = prefs.get(archivo, "");
            File_Dir = prefs.get(directorio, "");
            File_Name = prefs.get(nombre, "");
        }
        //Se muestra la ruta del archivo
        etiquetaArchivoA.setText("Archivo Actual: " + File_Path);
    }

    //Función que escribe en el archivo de preferencias la ruta del archivo
    public void escribirPreferencias(String r, String Dir, String Name) {
        Preferences prefs = Preferences.userNodeForPackage(ventanaVisitas.class);

        prefs.put(archivo, r);
        prefs.put(directorio, Dir);
        prefs.put(nombre, Name);
        prefs.putBoolean(leido, true);
    }

    //Funcion que genera la ventana donde se pregunta por que archivo quiere ser utilizado
    public void selectFile() {
        JFileChooser chooser = new JFileChooser("");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos CSV", "csv");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File a = chooser.getSelectedFile();
            File_Path = a.getAbsolutePath();

            File_Dir = a.getParent();
            String temp = a.getName();
            File_Name = temp.substring(0, temp.length() - 4);
            escribirPreferencias(File_Path, File_Dir, File_Name);
            JOptionPane.showMessageDialog(null, "Archivo: " + a.getName() + " abierto con exito");
            etiquetaArchivoA.setText("Archivo Actual: " + File_Path);
        }
        calculaProxima();
    }

    //Funcion que genera ventanas y cargadatos en la tabla de dicha ventana
    public void cargaDatosN() {

        int entradas = 0;
        MostrarVisitas m = new MostrarVisitas();
        DefaultTableModel modelo1 = (DefaultTableModel) resetTable(m);
        for (visitasOrdinarias vis : visitas) {

            if (vis.getId() != 0) {
                Vector<Comparable> contenido = new Vector<Comparable>();
                contenido.add(vis.getId());
                contenido.add(vis.getNombre_parroquia());
                contenido.add(vis.getDescripcion());
                contenido.add(vis.getDecanato());
                contenido.add(vis.getDireccion());
                modelo1.addRow(contenido);
                entradas++;
            }
        }
        m.etiquetaEntradas.setText("Numero de entradas encontradas: " + entradas);
        m.setVisible(true);
    }

    ///Funcion que Selecciona la visita Actual
    private void rellenarCampos(int index) {
        //Path, variable da forma a la ruta del archivo
        String path = File_Dir + "\\" + File_Name + "\\", Opath = File_Dir + "\\" + File_Name + "\\";

        try {
            noVisita.setSelectedIndex(visitas[index].getId() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        proximaP.setText("Nombre Parroquia: " + visitas[index].getNombre_parroquia());
        proximaDes.setText("Descripción: " + visitas[index].getDescripcion());
        proximaDir.setText("Dirección: " + visitas[index].getDireccion());
        proximoDec.setText("Decanato: " + visitas[index].getDecanato());
        //Variable que guarda la imagen
        ImageIcon img = new ImageIcon(Opath + visitas[index].getImg());

        //Si la imagen no fue encontrada, es deciar tamaño 0
        if (img.getIconWidth() == -1) {

            //Colocar un placeholder
            img = new ImageIcon("/visitas/imagenes/placeholder.png");
            visitas[index].setTieneImg(false);
        }
        imagenP.setIcon(img);
        v_actual = index;

    }

    //Función que revisa si hay imagenes para colocar
    private void revisarImgs() {

        String path, Opath = File_Dir + "\\" + File_Name + "\\";
        //Por defecto el programa busca en directorios con el mismo nombre que
        //el archivo que abrimos, por ejemplo, Visitas_202X.csv, directiorio
        // PATH\\Visitas_202X\\
        int x = 0;

        for (visitasOrdinarias v : visitas) {
            path = "";
            //esta condición refina los numeros del 1 al 9, por si acaso no
            //tienen 0 al principio, de manera que no haya problemas con 10 y 1
            if (v.getId() < 10) {
                path = path + "0" + Integer.toString(v.getId()) + ".png";
            } else {
                path = path + Integer.toString(v.getId()) + ".png";
            }

            ImageIcon img = new ImageIcon(Opath + path);
            //En caso que no se encuentre la imagen, es decir que tenga tamaño
            //de -1, que coloque una imagen placeholder
            if (img.getIconWidth() == -1) {
                v.setTieneImg(false);
                v.setImg("placeholder.png");
            } else {
                v.setTieneImg(true);
                v.setImg(path);
            }
            x++;
        }
    }

    private void rellenaCombo() {
        //Funcion que rellena el Combobox del Id de las visitas
        noVisita.removeAllItems();
        for (int x = 0; x < visitasOrdinarias.getNum_parroquias() + 1; x++) {
            noVisita.addItem("" + (x + 1));
        }
    }

    //Función que calcula la siguiente visita en el calendario
    public void calculaProxima() {
        //Si no es tiempo de visitas, determinado en otra parte, notificar al
        //usuario y moestrar la ultima visita
        if (visitasOrdinarias.isTiempo_visitas() == false) {
            int index = visitasOrdinarias.getNum_parroquias();
            rellenarCampos(index);
            etiquetaProximaV.setText("Aún no es temporada de visitas");
            return;
        }
        //En caso contrario mostrar la visita y rellenas los campos
        etiquetaProximaV.setText("Próxima Visita");
        int n_visitas = 0, index_visita[] = new int[2], i = 0;
        LocalDateTime date = tiempo;

        for (int x = 0; x < visitas.length; x++) {
            if (visitas[x].getFecha() != null) {

                if (visitas[x].getFecha().format(dtf).equals(date.format(dtf))) {
                    index_visita[n_visitas] = x;
                    n_visitas += 1;
                }
            }
        }
        //Si la hora es despues de las 8 PM, bugeado quizas por la version del JDK
        if (tiempo.isBefore(date.withHour(20).withMinute(0))) {
            switch (n_visitas) {
                case 1:
                    i = 0;
                    break;
                case 2: //En caso de que haya dos visitas en el mismo dia
                    if (tiempo.isBefore(date.withHour(13).withMinute(0))) {
                        /*Si la hora es menor a las 5 PM*/
                        i = 0;
                    } else {
                        i = 1;
                    }
                    break;
                case 0:
                    etiquetaProximaV.setText("Ultima Visita");
                    index_visita[0] = visitasOrdinarias.getNum_parroquias() - 1;
                    break;
            }
            //Guardamos el indice de la visita actual
            v_actual = index_visita[i];
        } else {
            //Guardamos el indice de la siguiente vista
            v_actual = index_visita[i] + 1;
        }

        //Mostrar el resultado en la pantalla principal
        rellenarCampos(v_actual);

        //Si el numero de visita actual es la ultima visita
        if (v_actual == visitasOrdinarias.getNum_parroquias() - 1) {
            v_actual = 0;
        }
    }

    private void calculaTiempo() {//Función que Calcula el temporizador inferior
        String[] fechasI = {"la romería", "el inicio de las visitas"};
        int modo = 0;
        dtf2 = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm").localizedBy(Locale.US);
        LocalDateTime ahora = tiempo,
                romeria = LocalDateTime.parse("12/Oct/" + ahora.getYear() + " 06:00", dtf2),
                ny = LocalDateTime.of(ahora.getYear(), Month.DECEMBER, 31, 23, 59),
                inicio = LocalDateTime.of(ahora.getYear(), Month.MAY, 20, 17, 0);
        Duration diff = null;
        //Del 13-oct - 31 Dic
        if (ahora.isBefore(ny) && ahora.isAfter(romeria)) {
            diff = Duration.between(ahora, inicio.plusYears(1));
            ahora = ahora.plusYears(1);
            modo = 1;
            visitasOrdinarias.setTiempo_visitas(false);
        }//Durante las visitas
        if (ahora.isAfter(inicio) && ahora.isBefore(romeria.plusDays(1))) {
            diff = Duration.between(ahora, romeria);
            modo = 0;
            visitasOrdinarias.setTiempo_visitas(true);
        }//Antes de las visitas y despues de año nuevo
        if (ahora.isBefore(inicio)) {
            diff = Duration.between(ahora, inicio);
            modo = 1;
            visitasOrdinarias.setTiempo_visitas(false);
        }
        try {
            long temp = diff.toDays();
            switch ((int) temp) {
                case 0:
                    etiquetaDias.setText("Faltan: " + diff.toHours() + " horas para " + fechasI[modo] + " " + ahora.getYear());
                    break;
                case 1:
                    etiquetaDias.setText("Faltan: " + diff.toDaysPart() + " dias, " + diff.toHoursPart() + "hrs. para " + fechasI[modo] + " " + ahora.getYear());
                    break;
                default:
                    etiquetaDias.setText("Faltan: " + diff.toDaysPart() + " días, " + diff.toHoursPart() + "hrs. para " + fechasI[modo] + " " + ahora.getYear());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al calcular las fechas", "Error al calcular las fechas", JOptionPane.ERROR_MESSAGE);
        }

    }
    //Modelo de la tabla que se usa para mostrar las visitas

    public DefaultTableModel resetTable(MostrarVisitas m) {
        DefaultTableModel modeloEspecial = new DefaultTableModel();
        m.tablaConsulta2.setModel(modeloEspecial);
        ArrayList<Object> encabezados = new ArrayList<Object>();
        encabezados.add("No.");
        encabezados.add("Nombre");
        encabezados.add("Descripción");
        encabezados.add("Decanato");
        encabezados.add("Dirección");
        for (Object columna : encabezados) {
            modeloEspecial.addColumn(columna);
        }
        m.tablaConsulta2.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        // set preferred column widths
        //Columna ID
        m.tablaConsulta2.getColumnModel().getColumn(0).setResizable(false);
        m.tablaConsulta2.getColumnModel().getColumn(0).setPreferredWidth(31);
        m.tablaConsulta2.setRowHeight(25);
        //Columna Nombres
        m.tablaConsulta2.getColumnModel().getColumn(1).setResizable(false);
        m.tablaConsulta2.getColumnModel().getColumn(1).setPreferredWidth(380);
        //Columna Fechas
        m.tablaConsulta2.getColumnModel().getColumn(2).setResizable(false);
        m.tablaConsulta2.getColumnModel().getColumn(2).setPreferredWidth(275);
        //Columna Decanato
        m.tablaConsulta2.getColumnModel().getColumn(3).setResizable(false);
        m.tablaConsulta2.getColumnModel().getColumn(3).setPreferredWidth(200);
        //Columna Dirección
        m.tablaConsulta2.getColumnModel().getColumn(4).setResizable(false);
        m.tablaConsulta2.getColumnModel().getColumn(4).setPreferredWidth(800);
        return modeloEspecial;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        busqueda = new javax.swing.JTextField();
        botonBuscar = new javax.swing.JButton();
        buscarPor = new javax.swing.JComboBox<>();
        actualizarT = new javax.swing.JButton();
        botonAbrirA = new javax.swing.JButton();
        etiquetaArchivoA = new javax.swing.JLabel();
        etiquetaProximaV = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        botonProxima = new javax.swing.JButton();
        botonHoy = new javax.swing.JButton();
        botonAnterior = new javax.swing.JButton();
        etiquetaDias = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        proximoDec = new javax.swing.JLabel();
        proximaP = new javax.swing.JLabel();
        proximaDes = new javax.swing.JLabel();
        proximaDir = new javax.swing.JLabel();
        etiquetaN = new javax.swing.JLabel();
        noVisita = new javax.swing.JComboBox<>();
        imagenP = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuAbrir = new javax.swing.JMenuItem();
        menuActualizar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuAcercade = new javax.swing.JMenuItem();
        MenuEdicion = new javax.swing.JMenu();
        addEntrada = new javax.swing.JMenuItem();
        menuEditar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        busqueda.setToolTipText("Ingresa busqueda");

        botonBuscar.setText("Buscar");
        botonBuscar.setToolTipText("Buscar Nombre parroquia");
        botonBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });

        buscarPor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nombre Parroquia", "Fecha", "Decanato" }));
        buscarPor.setToolTipText("Buscar por Nombre Parroquia, Fecha o Decanato");
        buscarPor.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        actualizarT.setText("Mostrar todas");
        actualizarT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarTActionPerformed(evt);
            }
        });

        botonAbrirA.setText("Abrir Archivo");
        botonAbrirA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAbrirAActionPerformed(evt);
            }
        });

        etiquetaArchivoA.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        etiquetaArchivoA.setText("Archivo Actual:");

        etiquetaProximaV.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        etiquetaProximaV.setText("Próxima visita");

        botonProxima.setText("->");
        botonProxima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonProximaActionPerformed(evt);
            }
        });

        botonHoy.setText("Hoy");
        botonHoy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonHoyActionPerformed(evt);
            }
        });

        botonAnterior.setText("<-");
        botonAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnteriorActionPerformed(evt);
            }
        });
        botonAnterior.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                botonAnteriorKeyPressed(evt);
            }
        });

        etiquetaDias.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        etiquetaDias.setText("Dias para:");
        etiquetaDias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaDiasMouseClicked(evt);
            }
        });

        proximoDec.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        proximoDec.setText("Decanato:");
        proximoDec.setToolTipText("Click para buscar por este decanato");
        proximoDec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proximoDecMouseClicked(evt);
            }
        });

        proximaP.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        proximaP.setText("Nombre Parroquia:");

        proximaDes.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        proximaDes.setText("Descripción:");

        proximaDir.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        proximaDir.setText("Dirección:");

        etiquetaN.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        etiquetaN.setText("No.;");

        noVisita.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        noVisita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {}));
        noVisita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noVisitaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagenP, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(etiquetaN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(noVisita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(proximaDir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proximaDes, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proximaP, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                    .addComponent(proximoDec, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(noVisita, javax.swing.GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)
                            .addComponent(etiquetaN))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proximaP, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proximaDes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proximaDir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(proximoDec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(imagenP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiquetaProximaV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(etiquetaArchivoA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(actualizarT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonAbrirA)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscarPor, 0, 744, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(busqueda)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(botonAnterior)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonHoy)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonProxima)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(etiquetaDias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(botonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buscarPor, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(actualizarT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAbrirA, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiquetaProximaV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonProxima, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiquetaDias))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(etiquetaArchivoA)
                .addContainerGap())
        );

        jMenu1.setText("Archivo");

        menuAbrir.setText("Abrir");
        menuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirActionPerformed(evt);
            }
        });
        jMenu1.add(menuAbrir);

        menuActualizar.setText("Actualizar");
        menuActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuActualizarActionPerformed(evt);
            }
        });
        jMenu1.add(menuActualizar);
        jMenu1.add(jSeparator3);

        menuAcercade.setText("Acerca de");
        menuAcercade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAcercadeActionPerformed(evt);
            }
        });
        jMenu1.add(menuAcercade);

        jMenuBar1.add(jMenu1);

        MenuEdicion.setText("Editar");

        addEntrada.setText("Añadir");
        addEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEntradaActionPerformed(evt);
            }
        });
        MenuEdicion.add(addEntrada);

        menuEditar.setText("Editar");
        menuEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEditarActionPerformed(evt);
            }
        });
        MenuEdicion.add(menuEditar);

        jMenuBar1.add(MenuEdicion);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed
        // TODO add your handling code here:
        //Función del boton abrir en el menú superior, misma que seleciona
        //otro archivo
        selectFile();

    }//GEN-LAST:event_menuAbrirActionPerformed

    private void menuAcercadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAcercadeActionPerformed

        //Función que muestra información del programa
    }//GEN-LAST:event_menuAcercadeActionPerformed

    private void etiquetaDiasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaDiasMouseClicked
        // TODO add your handling code here:
        calculaTiempo();
    }//GEN-LAST:event_etiquetaDiasMouseClicked

    private void botonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnteriorActionPerformed
        // TODO add your handling code here:
        //Función que recorre la lista de visitas hacia atrás
        if (v_actual > 0) {
            v_actual--;
        } else {
            v_actual = visitasOrdinarias.getNum_parroquias();
        }
        rellenarCampos(v_actual);
    }//GEN-LAST:event_botonAnteriorActionPerformed

    private void botonHoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonHoyActionPerformed
        // TODO add your handling code here:
        //Evento que manda a llamar la función que calcula la siguiente visita
        calculaProxima();
    }//GEN-LAST:event_botonHoyActionPerformed

    private void botonProximaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonProximaActionPerformed
        // TODO add your handling code here:
        //Función que recorre la lista de visitas hacia adelante
        if (v_actual < visitasOrdinarias.getNum_parroquias()) {
            v_actual++;
        } else {
            v_actual = 0;
        }

        rellenarCampos(v_actual);
    }//GEN-LAST:event_botonProximaActionPerformed

    private void botonAbrirAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAbrirAActionPerformed
        // TODO add your handling code here:
        //Esta función seleciona otro archivo
        selectFile();
    }//GEN-LAST:event_botonAbrirAActionPerformed

    private void actualizarTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarTActionPerformed
        // TODO add your handling code here:
        //Esta función refresca la lista de visitas, en caso de que añadamos o modifiquemos otra
        cargaDatosN();
    }//GEN-LAST:event_actualizarTActionPerformed

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarActionPerformed
        // TODO add your handling code here:
        //Función que busca en la lista de visitas según se indique en las combo
        //boxes de abajo de la barra
        buscar();
    }//GEN-LAST:event_botonBuscarActionPerformed

    private void menuActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuActualizarActionPerformed
        // TODO add your handling code here:
        //Esta función actualiza la lista de visitas
        abrir();
    }//GEN-LAST:event_menuActualizarActionPerformed

    private void botonAnteriorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_botonAnteriorKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_botonAnteriorKeyPressed

    private void noVisitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noVisitaActionPerformed
        // TODO add your handling code here:
        //Función que controla el funcionamiento del Combobox
        JComboBox cb = (JComboBox) evt.getSource();
        int index = 0;
        try {
            index = Integer.parseInt((String) cb.getSelectedItem());
        } catch (NumberFormatException numberFormatException) {
            //numberFormatException.printStackTrace();
        }
        if (index != 0) {
            rellenarCampos(index - 1);
        }
    }//GEN-LAST:event_noVisitaActionPerformed

    private void proximoDecMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proximoDecMouseClicked
        // TODO add your handling code here:
        String temp = "";
        if (proximoDec.getText() != null) {
            temp = proximoDec.getText();
        }

        try {
            temp = temp.substring(10);
            buscarPorDecanato(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_proximoDecMouseClicked

    private void addEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEntradaActionPerformed
        // TODO add your handling code here:
        //esta función crea una instancia de la ventana que contiene el formulario
        ///en donde se añaden nuevas visitas
        AddVisita adV = new AddVisita(visitas, ventana);
        adV.setVisible(true);

    }//GEN-LAST:event_addEntradaActionPerformed

    private void menuEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEditarActionPerformed
        // TODO add your handling code here:
        EditVisita editar = new EditVisita(visitas, ventana);
        editar.setVisible(true);
    }//GEN-LAST:event_menuEditarActionPerformed

    private void addVisita(String NombreP, String Desc, String Decanato, String Direccion, int indice) {
        visitas[indice].setNombre_parroquia(NombreP);
        visitas[indice].setDescO(Desc);
        visitas[indice].setDecanato(Decanato);
        visitas[indice].setDireccion(Direccion);
    }

    private void DimeDecanatos() {
        ArrayList<String> nDecanatos = new ArrayList<String>();
        int cont = 0;
        for (visitasOrdinarias v : visitas) {
            if (v.getDecanato() != null && !nDecanatos.contains(v.getDecanato())) {
                nDecanatos.add(v.getDecanato());
                cont += 1;
            }

        }

    }

    void escribirCSV() {
        //Función que Añade TODO el areglo actualizado al archivo CSV
        //Sobreescribe Todo el Archivo
        FileWriter escritor = null;
        try {

            escritor = new FileWriter(File_Path);
            //Añadir encabezados
            escritor.append("Nombre");
            escritor.append(";");
            escritor.append("Descripción");
            escritor.append(";");
            escritor.append("Decanato");
            escritor.append(";");
            escritor.append("Dirección");
            escritor.append(";");
            escritor.append("Imagen");
            escritor.append(";");

            //Añadir la lista actualizada
            for (int x = 0; x <= visitasOrdinarias.getNum_parroquias(); x++) {
                escritor.append("\n");
                escritor.append(visitas[x].getNombre_parroquia() + ";");
                escritor.append(visitas[x].getDescO() + ";");
                escritor.append(visitas[x].getDecanato() + ";");
                escritor.append(visitas[x].getDireccion() + ";");
                escritor.append(visitas[x].getImg() + ";");

            }

            escritor.flush();
            escritor.close();
        } catch (IOException ex) {
            Logger.getLogger(ventanaVisitas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void buscar() {
        /* Función que busca en las visitas según lo que se haya seleccionado
        en el combobox*/
        String buscar = busqueda.getText();
        //Según lo selecionado en el combobox se va a la formula indicada
        switch (buscarPor.getSelectedIndex()) {
            case 0:
                buscarPorNombre(buscar);
                break;
            case 1:
                buscarPorDesc(buscar);
                break;
            case 2:
                buscarPorDecanato(buscar);
                break;
        }

    }
//Busqueda por nombre de parroquia

    private void buscarPorNombre(String busqueda) {
        int entradas = 0;
        MostrarVisitas m = new MostrarVisitas();
        DefaultTableModel modelo1 = (DefaultTableModel) resetTable(m);
        for (int x = 0; x < visitas.length; x++) {
            if (visitas[x].getNombre_parroquia() != null) {
                if (visitas[x].getNombre_parroquia().toLowerCase().contains(busqueda.toLowerCase())) {
                    Vector<Comparable> contenido = new Vector<Comparable>();
                    contenido.add(visitas[x].getId());
                    contenido.add(visitas[x].getNombre_parroquia());
                    contenido.add(visitas[x].getDescripcion());
                    contenido.add(visitas[x].getDecanato());
                    contenido.add(visitas[x].getDireccion());
                    modelo1.addRow(contenido);
                    entradas++;
                }
            }
        }
        m.etiquetaEntradas.setText("Numero de entradas encontradas: " + entradas);
        m.setVisible(true);
    }

    //Busqueda por el campo fecha/Descripción
    private void buscarPorDesc(String busqueda) {
        int entradas = 0;
        MostrarVisitas m = new MostrarVisitas();
        DefaultTableModel modelo1 = (DefaultTableModel) resetTable(m);
        for (int x = 0; x < visitas.length; x++) {
            if (visitas[x].getDescripcion() != null) {
                if (visitas[x].getDescripcion().toLowerCase().contains(busqueda.toLowerCase())) {
                    Vector<Comparable> contenido = new Vector<Comparable>();
                    contenido.add(visitas[x].getId());
                    contenido.add(visitas[x].getNombre_parroquia());
                    contenido.add(visitas[x].getDescripcion());
                    contenido.add(visitas[x].getDecanato());
                    contenido.add(visitas[x].getDireccion());
                    modelo1.addRow(contenido);
                    entradas++;
                }
            }
        }
        m.etiquetaEntradas.setText("Numero de entradas encontradas: " + entradas);
        m.setVisible(true);
    }

    //Busqueda por el campo decanato
    private void buscarPorDecanato(String busqueda) {
        MostrarVisitas m = new MostrarVisitas();
        int entradas = 0;
        DefaultTableModel modelo1 = (DefaultTableModel) resetTable(m);
        for (int x = 0; x < visitas.length; x++) {
            if (visitas[x].getDecanato() != null) {
                if (visitas[x].getDecanato().toLowerCase().contains(busqueda.toLowerCase())) {
                    Vector<Comparable> contenido = new Vector<Comparable>();
                    contenido.add(visitas[x].getId());
                    contenido.add(visitas[x].getNombre_parroquia());
                    contenido.add(visitas[x].getDescripcion());
                    contenido.add(visitas[x].getDecanato());
                    contenido.add(visitas[x].getDireccion());
                    modelo1.addRow(contenido);
                    entradas++;
                }
            }
        }
        m.etiquetaEntradas.setText("Numero de entradas encontradas: " + entradas);
        m.setVisible(true);
    }

    public void abrir() {
        try {
            openFileSC();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error - Archivo no encontrado", "Archivo no Encontrado\nPor favor especifique un archivo a continuaci�n", JOptionPane.ERROR_MESSAGE);
            selectFile();
        }

    }

    private void openFileSC() throws FileNotFoundException // Metodo para leer el archivo CSV y colocarlo en el arreglo de visitas
    {
        int index = 0, contadorv = 0;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("d/MMM/yyyy h:mm a").localizedBy(Locale.US);
        LocalDateTime temp = null;
        Scanner scanner = new Scanner(new File(File_Path)), dataScanner = null;

        inicializarVisitas();

        while (scanner.hasNextLine()) {

            dataScanner = new Scanner(scanner.nextLine());
            dataScanner.useDelimiter(";");//Seleccionar Delimitador para el archivo CSV, cambiado "," por ";"

            while (dataScanner.hasNext()) {
                String data = dataScanner.next();
                if (contadorv > 0) {
                    switch (index) {
                        case 0:
                            //Analizar Nombre de la Parroquia
                            visitas[contadorv - 1].setNombre_parroquia(data);
                            break;
                        case 1:
                            //Analizar Descripción o Fecha
                            try {
                            temp = LocalDateTime.parse(data, format);
                            visitas[contadorv - 1].setFecha(temp);
                            visitas[contadorv - 1].setDescripcion(temp.format(formatoDiaS));
                            visitas[contadorv - 1].setDescO(data);
                        } catch (Exception e) {
                            //En caso de que no pueda ser analizada la fecha
                            scanner.close();
                            e.printStackTrace();
                            String temp2 = "Error - " + "\"" + data + "\" No pudo ser analizado";
                            JOptionPane.showMessageDialog(rootPane, temp2, "Error al analizar archivo", JOptionPane.ERROR_MESSAGE);
                            switch (JOptionPane.showConfirmDialog(rootPane, "Intentar con otro archivo", "¿Volver a Intentar?", JOptionPane.YES_NO_OPTION)) {
                                case JOptionPane.YES_OPTION: {
                                    selectFile();
                                    abrir();
                                    return;
                                }
                                case JOptionPane.NO_OPTION: {
                                    System.exit(0);

                                }
                            }

                        }
                        break;
                        case 2:
                            //Analizar Decanato
                            visitas[contadorv - 1].setDecanato(data);

                            break;
                        case 3:
                            //Analizar la dirección
                            visitas[contadorv - 1].setDireccion(data);
                            break;
                    }
                    index++;
                    //Colocar el numero de indice
                    visitas[contadorv - 1].setId(contadorv);
                }
            }
            index = 0;
            contadorv++;
        }
        scanner.close();
        visitasOrdinarias.setNum_parroquias(contadorv - 2);
        //Rellenar el combobox del ID de parroquia
        rellenaCombo();
        //Revisar las imagenes
        revisarImgs();
    }

    void mensajeError(int x) {
        //String Mensaje[] = {""]
        JOptionPane.showMessageDialog(rootPane, "Error - Revisar Archivo por errores en Fechas", "Error al abrir archivo", JOptionPane.ERROR_MESSAGE);
        switch (JOptionPane.showConfirmDialog(rootPane, "Intentar con otro archivo", "¿Volver a Intentar?", JOptionPane.YES_NO_OPTION)) {
            case JOptionPane.YES_OPTION: {
                selectFile();
                abrir();
                return;
            }
            case JOptionPane.NO_OPTION: {
                System.exit(0);

            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        dtf = DateTimeFormatter.ofPattern("d/MMM/uuuu").localizedBy(Locale.US);
        //tiempo = LocalDateTime.now();

        /* Set the look and feel */
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            // handle exception
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ventanaVisitas v = new ventanaVisitas();
                v.setVisible(true);
                v.setLocationRelativeTo(null);
                ventana = v;
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu MenuEdicion;
    private javax.swing.JButton actualizarT;
    private javax.swing.JMenuItem addEntrada;
    private javax.swing.JButton botonAbrirA;
    private javax.swing.JButton botonAnterior;
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonHoy;
    private javax.swing.JButton botonProxima;
    private javax.swing.JComboBox<String> buscarPor;
    private javax.swing.JTextField busqueda;
    private javax.swing.JLabel etiquetaArchivoA;
    private javax.swing.JLabel etiquetaDias;
    private javax.swing.JLabel etiquetaN;
    private javax.swing.JLabel etiquetaProximaV;
    private javax.swing.JLabel imagenP;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenuItem menuAcercade;
    private javax.swing.JMenuItem menuActualizar;
    private javax.swing.JMenuItem menuEditar;
    private javax.swing.JComboBox<String> noVisita;
    private javax.swing.JLabel proximaDes;
    private javax.swing.JLabel proximaDir;
    private javax.swing.JLabel proximaP;
    private javax.swing.JLabel proximoDec;
    // End of variables declaration//GEN-END:variables
}
