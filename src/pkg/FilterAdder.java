package pkg;
import javax.swing.*;

public class FilterAdder extends JFrame{
    private JLabel l_red;
    private JLabel l_green;
    private JLabel l_blue;
    private JLabel l_blur;
    private JPanel panel_all;
    private JSlider sld_red;
    private JSlider sld_green;
    private JSlider sld_blue;
    private JButton btn_reset;
    private JButton btn_save;
    private JSlider sld_blur;


    private int r, g, b;

    public FilterAdder(Image img){
        super("Add Filter");

        setContentPane(panel_all);
        pack();

        //set UI elems to actual values
         sld_blue.setValue(img.getB());
          sld_red.setValue(img.getR());
        sld_green.setValue(img.getG());
         sld_blur.setValue(img.getBlur());


        //Add action and change listeners
        btn_save.addActionListener(e -> setVisible(false));

        btn_reset.addActionListener(e -> {
            sld_blue.setValue(Image.defaultTint);
            sld_red.setValue(Image.defaultTint);
            sld_green.setValue(Image.defaultTint);
            sld_blur.setValue(Image.defaultBlur);
        });

        sld_blue.addChangeListener(e -> img.setB(sld_blue.getValue()));
        sld_green.addChangeListener(e -> img.setG(sld_green.getValue()));
        sld_red.addChangeListener(e -> img.setR(sld_red.getValue()));
        sld_blur.addChangeListener(e -> img.setBlur(sld_blur.getValue()));

        //show dialog
        setVisible(true);
    }

}
