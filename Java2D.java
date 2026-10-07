import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Java2D extends JFrame {

    JTextField texto;
    JSpinner tamaño;
    JCheckBox negrita;
    JCheckBox cursiva;
    JCheckBox subrayado;
    JCheckBox sombra;
    JSlider perspectiva;
    JComboBox<String> estilo;

    JButton colorTexto;
    JButton colorSombra;

    Color colorActual = Color.BLUE;
    Color colorSombraActual = Color.GRAY;

    PanelWordArt panel;

    public Java2D() {

        setTitle("Editor de Texto 2D - WordArt");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // -----------------------------------------
        // PANEL DE CONTROLES
        // -----------------------------------------

        JPanel controles = new JPanel();

        controles.setLayout(new GridBagLayout());

        controles.setBorder(
            BorderFactory.createTitledBorder(
                "Opciones de formato WordArt"
            )
        );

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // -----------------------------------------
        // TEXTO
        // -----------------------------------------

        c.gridx = 0;
        c.gridy = 0;

        controles.add(
            new JLabel("Texto:"),
            c
        );

        texto = new JTextField("Mi WordArt");

        c.gridx = 1;
        c.gridy = 0;

        c.gridwidth = 3;

        controles.add(
            texto,
            c
        );

        // -----------------------------------------
        // TAMAÑO
        // -----------------------------------------

        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 1;

        controles.add(
            new JLabel("Tamaño:"),
            c
        );

        tamaño = new JSpinner(
            new SpinnerNumberModel(
                60,
                10,
                200,
                5
            )
        );

        c.gridx = 1;
        c.gridy = 1;

        controles.add(
            tamaño,
            c
        );

        // -----------------------------------------
        // NEGRITA
        // -----------------------------------------

        negrita = new JCheckBox("Negrita");

        c.gridx = 2;
        c.gridy = 1;

        controles.add(
            negrita,
            c
        );

        // -----------------------------------------
        // CURSIVA
        // -----------------------------------------

        cursiva = new JCheckBox("Cursiva");

        c.gridx = 3;
        c.gridy = 1;

        controles.add(
            cursiva,
            c
        );

        // -----------------------------------------
        // SUBRAYADO
        // -----------------------------------------

        subrayado = new JCheckBox("Subrayado");

        c.gridx = 0;
        c.gridy = 2;

        controles.add(
            subrayado,
            c
        );

        // -----------------------------------------
        // SOMBRA
        // -----------------------------------------

        sombra = new JCheckBox("Aplicar sombra");

        sombra.setSelected(true);

        c.gridx = 1;
        c.gridy = 2;

        controles.add(
            sombra,
            c
        );

        // -----------------------------------------
        // COLOR DEL TEXTO
        // -----------------------------------------

        colorTexto = new JButton(
            "Color del texto"
        );

        c.gridx = 2;
        c.gridy = 2;

        controles.add(
            colorTexto,
            c
        );

        colorTexto.addActionListener(e -> {

            Color nuevoColor =
                JColorChooser.showDialog(
                    this,
                    "Seleccionar color del texto",
                    colorActual
                );

            if (nuevoColor != null) {

                colorActual = nuevoColor;

                actualizar();
            }
        });

        // -----------------------------------------
        // COLOR DE SOMBRA
        // -----------------------------------------

        colorSombra = new JButton(
            "Color de sombra"
        );

        c.gridx = 3;
        c.gridy = 2;

        controles.add(
            colorSombra,
            c
        );

        colorSombra.addActionListener(e -> {

            Color nuevoColor =
                JColorChooser.showDialog(
                    this,
                    "Seleccionar color de sombra",
                    colorSombraActual
                );

            if (nuevoColor != null) {

                colorSombraActual =
                    nuevoColor;

                actualizar();
            }
        });

        // -----------------------------------------
        // ESTILO
        // -----------------------------------------

        c.gridx = 0;
        c.gridy = 3;

        controles.add(
            new JLabel("Estilo:"),
            c
        );

        estilo = new JComboBox<>(
            new String[] {
                "Normal",
                "Contorno",
                "Doble contorno",
                "Sombra desplazada",
                "3D"
            }
        );

        c.gridx = 1;
        c.gridy = 3;

        c.gridwidth = 2;

        controles.add(
            estilo,
            c
        );

        estilo.addActionListener(
            e -> actualizar()
        );

        // -----------------------------------------
        // PERSPECTIVA
        // -----------------------------------------

        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 4;

        controles.add(
            new JLabel("Perspectiva:"),
            c
        );

        perspectiva = new JSlider(
            -40,
            40,
            0
        );

        perspectiva.setMajorTickSpacing(20);
        perspectiva.setMinorTickSpacing(5);

        perspectiva.setPaintTicks(true);
        perspectiva.setPaintLabels(true);

        c.gridx = 1;
        c.gridy = 4;

        c.gridwidth = 3;

        controles.add(
            perspectiva,
            c
        );

        perspectiva.addChangeListener(
            e -> actualizar()
        );

        // -----------------------------------------
        // BOTÓN ACTUALIZAR
        // -----------------------------------------

        JButton actualizar =
            new JButton("Actualizar");

        c.gridx = 0;
        c.gridy = 5;

        c.gridwidth = 2;

        controles.add(
            actualizar,
            c
        );

        actualizar.addActionListener(
            e -> actualizar()
        );

        // -----------------------------------------
        // BOTÓN RESTABLECER
        // -----------------------------------------

        JButton restablecer =
            new JButton("Restablecer");

        c.gridx = 2;
        c.gridy = 5;

        c.gridwidth = 2;

        controles.add(
            restablecer,
            c
        );

        restablecer.addActionListener(e -> {

            texto.setText("Mi WordArt");

            tamaño.setValue(60);

            negrita.setSelected(false);

            cursiva.setSelected(false);

            subrayado.setSelected(false);

            sombra.setSelected(true);

            perspectiva.setValue(0);

            estilo.setSelectedIndex(0);

            colorActual = Color.BLUE;

            colorSombraActual = Color.GRAY;

            actualizar();
        });

        // -----------------------------------------
        // PANEL DE DIBUJO
        // -----------------------------------------

        panel = new PanelWordArt();

        setLayout(
            new BorderLayout()
        );

        add(
            controles,
            BorderLayout.NORTH
        );

        add(
            panel,
            BorderLayout.CENTER
        );

        // Actualizar al escribir
        texto.addActionListener(
            e -> actualizar()
        );
    }

    // ---------------------------------------------
    // ACTUALIZAR
    // ---------------------------------------------

    void actualizar() {

        panel.repaint();
    }

    // =============================================
    // PANEL WORDART
    // =============================================

    class PanelWordArt extends JPanel {

        public PanelWordArt() {

            setBackground(
                Color.WHITE
            );

            setBorder(
                BorderFactory.createTitledBorder(
                    "Vista previa 2D"
                )
            );
        }

        @Override
        protected void paintComponent(
            Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                (Graphics2D) g.create();

            // -------------------------------------
            // ANTIALIASING
            // -------------------------------------

            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            // -------------------------------------
            // OBTENER TEXTO
            // -------------------------------------

            String palabra =
                texto.getText();

            // -------------------------------------
            // TAMAÑO
            // -------------------------------------

            int tamañoFuente =
                (Integer) tamaño.getValue();

            // -------------------------------------
            // TIPO DE FUENTE
            // -------------------------------------

            int tipo = Font.PLAIN;

            if (negrita.isSelected()) {

                tipo |= Font.BOLD;
            }

            if (cursiva.isSelected()) {

                tipo |= Font.ITALIC;
            }

            Font fuente =
                new Font(
                    "Arial",
                    tipo,
                    tamañoFuente
                );

            g2.setFont(fuente);

            FontMetrics fm =
                g2.getFontMetrics();

            int ancho =
                fm.stringWidth(palabra);

            int alto =
                fm.getHeight();

            int x =
                (getWidth() - ancho) / 2;

            int y =
                (getHeight() + alto) / 2;

            // -------------------------------------
            // PERSPECTIVA
            // -------------------------------------

            double valorPerspectiva =
                perspectiva.getValue() / 100.0;

            AffineTransform transformacion =
                new AffineTransform();

            transformacion.translate(
                getWidth() / 2.0,
                getHeight() / 2.0
            );

            transformacion.shear(
                valorPerspectiva,
                0
            );

            transformacion.translate(
                -getWidth() / 2.0,
                -getHeight() / 2.0
            );

            g2.transform(
                transformacion
            );

            // -------------------------------------
            // SOMBRA
            // -------------------------------------

            if (sombra.isSelected()) {

                g2.setColor(
                    colorSombraActual
                );

                g2.drawString(
                    palabra,
                    x + 8,
                    y + 8
                );
            }

            // -------------------------------------
            // OBTENER ESTILO
            // -------------------------------------

            String seleccionado =
                (String) estilo.getSelectedItem();

            // -------------------------------------
            // CONTORNO
            // -------------------------------------

            if (seleccionado.equals(
                "Contorno"
            )) {

                g2.setColor(
                    Color.BLACK
                );

                g2.drawString(
                    palabra,
                    x - 2,
                    y
                );

                g2.drawString(
                    palabra,
                    x + 2,
                    y
                );

                g2.drawString(
                    palabra,
                    x,
                    y - 2
                );

                g2.drawString(
                    palabra,
                    x,
                    y + 2
                );
            }

            // -------------------------------------
            // DOBLE CONTORNO
            // -------------------------------------

            if (seleccionado.equals(
                "Doble contorno"
            )) {

                g2.setColor(
                    Color.BLACK
                );

                g2.drawString(
                    palabra,
                    x - 4,
                    y
                );

                g2.drawString(
                    palabra,
                    x + 4,
                    y
                );

                g2.drawString(
                    palabra,
                    x,
                    y - 4
                );

                g2.drawString(
                    palabra,
                    x,
                    y + 4
                );
            }

            // -------------------------------------
            // SOMBRA DESPLAZADA
            // -------------------------------------

            if (seleccionado.equals(
                "Sombra desplazada"
            )) {

                g2.setColor(
                    Color.DARK_GRAY
                );

                g2.drawString(
                    palabra,
                    x + 12,
                    y + 12
                );
            }

            // -------------------------------------
            // EFECTO 3D
            // -------------------------------------

            if (seleccionado.equals(
                "3D"
            )) {

                g2.setColor(
                    Color.GRAY
                );

                for (int i = 10; i > 0; i--) {

                    g2.drawString(
                        palabra,
                        x + i,
                        y + i
                    );
                }
            }

            // -------------------------------------
            // TEXTO PRINCIPAL
            // -------------------------------------

            g2.setColor(
                colorActual
            );

            g2.drawString(
                palabra,
                x,
                y
            );

            // -------------------------------------
            // SUBRAYADO
            // -------------------------------------

            if (subrayado.isSelected()) {

                int lineaY =
                    y + 5;

                g2.setStroke(
                    new BasicStroke(3)
                );

                g2.drawLine(
                    x,
                    lineaY,
                    x + ancho,
                    lineaY
                );
            }

            g2.dispose();
        }
    }

    // =============================================
    // MAIN
    // =============================================

    public static void main(
        String[] args
    ) {

        SwingUtilities.invokeLater(() -> {

            Java2D ventana =
                new Java2D();

            ventana.setVisible(true);
        });
    }
}