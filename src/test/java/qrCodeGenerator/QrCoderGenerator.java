package qrCodeGenerator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class QrCoderGenerator extends JFrame implements ActionListener {
	static QrCoderGenerator frame;
	private static final long serialVersionUID = 1L;
	private static final String formatoQrCodeGerado = "png";
	private JPanel contentPane;
	private JTextField txtToQrCode;
	private JButton btnGerarQrCode;
	private ImageIcon img;
	private Image image;
	private Image newImg;
	private Image ImageQR; 

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new QrCoderGenerator();
					frame.setVisible(true);
					frame.revalidate();
					frame.repaint();
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public QrCoderGenerator() {
		super("Gere seu QRCODE!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 275, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtToQrCode = new JTextField();
		txtToQrCode.setBounds(10, 21, 143, 20);
		contentPane.add(txtToQrCode);
		txtToQrCode.setColumns(10);

		btnGerarQrCode = new JButton("Gerar QrCode");
		btnGerarQrCode.setBounds(154, 20, 99, 23);
		contentPane.add(btnGerarQrCode);
		btnGerarQrCode.addActionListener(this);
		JLabel lblNewLabel = new JLabel("QRCODE");
		img = new ImageIcon(QrCoderGenerator.class.getResource("/qrCodeGenerator/QRCODE.png"));
		image = img.getImage();
		newImg = image.getScaledInstance(235, 235, java.awt.Image.SCALE_SMOOTH);
		lblNewLabel.setIcon(new ImageIcon(newImg));
		lblNewLabel.setBounds(10, 53, 235, 235);
		contentPane.add(lblNewLabel);

	}

	private void gerarComZXing(String texto) {
		try {

			File file = new File("src/test/java/qrCodeGenerator/QRCODE" + "." + formatoQrCodeGerado);
			Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, 100, 100, hintMap);
			int CrunchifyWidth = byteMatrix.getWidth();
			BufferedImage imageQR = new BufferedImage(CrunchifyWidth, CrunchifyWidth, BufferedImage.TYPE_INT_RGB);
			imageQR.createGraphics();
			Graphics2D graphics = (Graphics2D) imageQR.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < CrunchifyWidth; i++) {
				for (int j = 0; j < CrunchifyWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			frame.dispose();
			img = null;
			image = null;
			newImg = null;

			ImageIO.write(imageQR, formatoQrCodeGerado, file);

			JOptionPane.showMessageDialog(this, "QRCode gerado com sucesso!");
	
			frame.setVisible(true);
			frame.revalidate();
			frame.repaint();
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void gerarQrCode() {
		if (txtToQrCode.getText() == null || txtToQrCode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, " texto a ser codificado é obrigatório");
		} else {
			gerarComZXing(txtToQrCode.getText());

		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnGerarQrCode) {
			gerarQrCode();
		}
	}

}
