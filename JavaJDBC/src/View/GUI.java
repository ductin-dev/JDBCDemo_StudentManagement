package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Database.DatabaseInfo;
import Database.StudentManager;

public class GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JTable table;
    private JPanel updateAndAdd;
    private JLabel lblStudent;
    private JTextField textField;
    private JButton btnLoad;
    private Panel infomationPanel;
    private JLabel lblNewLabel_4;
    private JLabel lblName;
    private JTextField textField_1;
    private JLabel lblGender;
    private JRadioButton rdbtnM;
    private JRadioButton rdbtnF;
    private JLabel lblClass;
    private JTextField textField_2;
    private JLabel lblAdress;
    private JTextField textField_3;
    private JLabel lblEmail;
    private JTextField textField_4;
    private JLabel lblUpdate;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnPrint;
    private JLabel lblSatellite;

    // Table ___________________________________________________________________
    public Vector<String> header = new Vector<String>();
    public Vector<Vector<String>> data = new Vector<>();

    // ManagerList______________________________________________________________
    StudentManager manager = new StudentManager();

    // _________________________________________________________________________
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		try {
		    GUI frame = new GUI();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    public JButton getBtnLoad() {
	if (btnLoad == null) {
	    btnLoad = new JButton("LOAD");
	    btnLoad.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		    try {
			// Tạo giao diện cho người dùng nhập username và password của database
			JPanel inputPane = new JPanel();
			inputPane.setLayout(new GridLayout(0, 2, 2, 2));
			JLabel label1 = new JLabel("Type database username: ");
			label1.setFont(new Font("Tahoma", Font.BOLD, 18));
			JLabel label2 = new JLabel("Type database password: ");
			label2.setFont(new Font("Tahoma", Font.BOLD, 18));

			// Tạo nơi điền username và password
			JTextField userName = new JTextField();
			JTextField passWord = new JTextField();
			inputPane.add(label1);
			inputPane.add(userName);
			inputPane.add(label2);
			inputPane.add(passWord);
			if (JOptionPane.showConfirmDialog(contentPane, inputPane,
			                "To connect database, please type infomation below !", JOptionPane.YES_NO_OPTION,
			                JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {

			    // Lấy username và password
			    String user = userName.getText();
			    String pass = passWord.getText();
			    if ((user.isEmpty()) || (pass.isEmpty())) throw new Exception("Input was not correct");
			    DatabaseInfo.dbUser = user;
			    DatabaseInfo.dbPass = pass;
			} else throw new Exception("Canceled by user");

			// Tải dữ liệu từ database vào phần mềm
			data = manager.getAll();

			// Thiết kế bảng dữ liệu để hiển thị
			header = new Vector<String>();
			header.add("ID");
			header.add("Name");
			header.add("Gender");
			header.add("Class");
			header.add("Adress");
			header.add("Email");
			((DefaultTableModel) (table.getModel())).setDataVector(data, header);

			// Kích hoạt các chức năng Add, Delete, tắt phần thông báo
			btnAdd.setEnabled(true);
			btnDelete.setEnabled(true);
			infomationPanel.setVisible(false);

			// Thông báo thành công
			JOptionPane.showMessageDialog(contentPane, "Load sucess!");

		    } catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, "Load failure!\nDetails: " + e);
		    }
		}
	    });
	    btnLoad.setFont(new Font("Tahoma", Font.BOLD, 43));
	    btnLoad.setBackground(Color.RED);
	    btnLoad.setBounds(1126, 25, 290, 78);
	    btnLoad.setForeground(Color.WHITE);
	}
	return btnLoad;
    }

    public GUI() {
	initComponents();
    }

    private void initComponents() {
	setResizable(false);
	setTitle("Student Management");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 1450, 693);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	contentPane.add(getInfomationPanel());
	contentPane.add(getScrollPane());
	contentPane.add(getUpdateAndAdd());
	contentPane.add(getBtnLoad());
	this.contentPane.add(getBtnPrint());
	this.contentPane.add(getLblSatellite());
    }

    public JScrollPane getScrollPane() {
	if (scrollPane == null) {
	    scrollPane = new JScrollPane();
	    scrollPane.setViewportBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	    scrollPane.setBounds(12, 13, 1081, 632);
	    scrollPane.setViewportView(getTable());
	}
	return scrollPane;
    }

    public JTable getTable() {
	if (table == null) {
	    table = new JTable();
	    table.setRowSelectionAllowed(true);
	    table.setDefaultEditor(Object.class, null);
	    table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    table.setRowHeight(25);
	    table.setFont(new Font("Arial", Font.PLAIN, 18));
	}
	return table;
    }

    public JPanel getUpdateAndAdd() {
	if (updateAndAdd == null) {
	    updateAndAdd = new JPanel();
	    updateAndAdd.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
	    updateAndAdd.setBackground(Color.LIGHT_GRAY);
	    updateAndAdd.setBounds(1126, 130, 290, 390);
	    updateAndAdd.setLayout(null);
	    updateAndAdd.add(getLblStudent());
	    updateAndAdd.add(getTextField());
	    updateAndAdd.add(getLblName());
	    updateAndAdd.add(getTextField_1());
	    updateAndAdd.add(getLblGender());
	    updateAndAdd.add(getRdbtnM());
	    updateAndAdd.add(getRdbtnF());
	    updateAndAdd.add(getLblClass());
	    updateAndAdd.add(getTextField_2());
	    updateAndAdd.add(getLblAdress());
	    updateAndAdd.add(getTextField_3());
	    updateAndAdd.add(getLblEmail());
	    updateAndAdd.add(getTextField_4());
	    updateAndAdd.add(getLblUpdate());
	    updateAndAdd.add(getBtnAdd());
	    updateAndAdd.add(getBtnDelete());
	}
	return updateAndAdd;
    }

    public JLabel getLblStudent() {
	if (lblStudent == null) {
	    lblStudent = new JLabel("Student ID");
	    lblStudent.setBackground(Color.WHITE);
	    lblStudent.setForeground(Color.BLACK);
	    lblStudent.setFont(new Font("Tahoma", Font.BOLD, 20));
	    lblStudent.setBounds(12, 28, 111, 39);
	}
	return lblStudent;
    }

    public JTextField getTextField() {
	if (textField == null) {
	    textField = new JTextField();
	    textField.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    textField.setBounds(135, 28, 142, 39);
	    textField.setColumns(10);
	}
	return textField;
    }

    public Panel getInfomationPanel() {
	if (infomationPanel == null) {
	    infomationPanel = new Panel();
	    infomationPanel.setBounds(62, 69, 976, 529);
	    infomationPanel.setLayout(null);
	    infomationPanel.add(getLblNewLabel_4());
	}
	return infomationPanel;
    }

    public JLabel getLblNewLabel_4() {
	if (lblNewLabel_4 == null) {
	    lblNewLabel_4 = new JLabel("Click \"LOAD\" button first to load database");
	    lblNewLabel_4.setForeground(Color.GRAY);
	    lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
	    lblNewLabel_4.setBounds(291, 156, 445, 219);
	}
	return lblNewLabel_4;
    }

    public JLabel getLblName() {
	if (lblName == null) {
	    lblName = new JLabel("Name");
	    lblName.setHorizontalAlignment(SwingConstants.TRAILING);
	    lblName.setForeground(Color.BLACK);
	    lblName.setFont(new Font("Tahoma", Font.BOLD, 20));
	    lblName.setBackground(Color.WHITE);
	    lblName.setBounds(12, 80, 111, 39);
	}
	return lblName;
    }

    public JTextField getTextField_1() {
	if (textField_1 == null) {
	    textField_1 = new JTextField();
	    textField_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    textField_1.setColumns(10);
	    textField_1.setBounds(135, 80, 142, 39);
	}
	return textField_1;
    }

    public JLabel getLblGender() {
	if (lblGender == null) {
	    lblGender = new JLabel("Gender");
	    lblGender.setHorizontalAlignment(SwingConstants.TRAILING);
	    lblGender.setForeground(Color.BLACK);
	    lblGender.setFont(new Font("Tahoma", Font.BOLD, 20));
	    lblGender.setBackground(Color.WHITE);
	    lblGender.setBounds(12, 132, 111, 39);
	}
	return lblGender;
    }

    public JRadioButton getRdbtnM() {
	if (rdbtnM == null) {
	    rdbtnM = new JRadioButton("M");
	    rdbtnM.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		    rdbtnMActionPerformed(arg0);
		}
	    });
	    rdbtnM.setOpaque(false);
	    rdbtnM.setFont(new Font("Tahoma", Font.BOLD, 18));
	    rdbtnM.setBackground(Color.WHITE);
	    rdbtnM.setBounds(145, 133, 55, 39);
	}
	return rdbtnM;
    }

    public JRadioButton getRdbtnF() {
	if (rdbtnF == null) {
	    rdbtnF = new JRadioButton("F");
	    rdbtnF.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    rdbtnFActionPerformed(e);
		}
	    });
	    rdbtnF.setOpaque(false);
	    rdbtnF.setFont(new Font("Tahoma", Font.BOLD, 18));
	    rdbtnF.setBackground(Color.WHITE);
	    rdbtnF.setBounds(222, 133, 55, 39);
	}
	return rdbtnF;
    }

    public JLabel getLblClass() {
	if (lblClass == null) {
	    lblClass = new JLabel("Class");
	    lblClass.setHorizontalAlignment(SwingConstants.TRAILING);
	    lblClass.setForeground(Color.BLACK);
	    lblClass.setFont(new Font("Tahoma", Font.BOLD, 20));
	    lblClass.setBackground(Color.WHITE);
	    lblClass.setBounds(12, 183, 111, 39);
	}
	return lblClass;
    }

    public JTextField getTextField_2() {
	if (textField_2 == null) {
	    textField_2 = new JTextField();
	    textField_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    textField_2.setColumns(10);
	    textField_2.setBounds(135, 183, 142, 39);
	}
	return textField_2;
    }

    public JLabel getLblAdress() {
	if (lblAdress == null) {
	    lblAdress = new JLabel("Adress");
	    lblAdress.setHorizontalAlignment(SwingConstants.TRAILING);
	    lblAdress.setForeground(Color.BLACK);
	    lblAdress.setFont(new Font("Tahoma", Font.BOLD, 20));
	    lblAdress.setBackground(Color.WHITE);
	    lblAdress.setBounds(12, 235, 111, 39);
	}
	return lblAdress;
    }

    public JTextField getTextField_3() {
	if (textField_3 == null) {
	    textField_3 = new JTextField();
	    textField_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    textField_3.setColumns(10);
	    textField_3.setBounds(135, 235, 142, 39);
	}
	return textField_3;
    }

    public JLabel getLblEmail() {
	if (lblEmail == null) {
	    lblEmail = new JLabel("Email");
	    lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
	    lblEmail.setForeground(Color.BLACK);
	    lblEmail.setFont(new Font("Tahoma", Font.BOLD, 20));
	    lblEmail.setBackground(Color.WHITE);
	    lblEmail.setBounds(12, 287, 111, 39);
	}
	return lblEmail;
    }

    public JTextField getTextField_4() {
	if (textField_4 == null) {
	    textField_4 = new JTextField();
	    textField_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
	    textField_4.setColumns(10);
	    textField_4.setBounds(135, 287, 142, 39);
	}
	return textField_4;
    }

    public JLabel getLblUpdate() {
	if (lblUpdate == null) {
	    lblUpdate = new JLabel("Update and delete");
	    lblUpdate.setForeground(Color.DARK_GRAY);
	    lblUpdate.setFont(new Font("Tahoma", Font.ITALIC, 16));
	    lblUpdate.setBounds(80, 0, 150, 27);
	}
	return lblUpdate;
    }

    public JButton getBtnAdd() {
	if (btnAdd == null) {
	    btnAdd = new JButton("Add");
	    btnAdd.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		    btnAddActionPerformed(arg0);
		}
	    });
	    btnAdd.setEnabled(false);
	    btnAdd.setForeground(Color.ORANGE);
	    btnAdd.setFont(new Font("Tahoma", Font.BOLD, 18));
	    btnAdd.setBounds(0, 336, 142, 54);
	}
	return btnAdd;
    }

    public JButton getBtnDelete() {
	if (btnDelete == null) {
	    btnDelete = new JButton("Delete");
	    btnDelete.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    btnDeleteActionPerformed(e);
		}
	    });
	    btnDelete.setEnabled(false);
	    btnDelete.setForeground(Color.RED);
	    btnDelete.setFont(new Font("Tahoma", Font.BOLD, 18));
	    btnDelete.setBounds(145, 336, 145, 54);
	}
	return btnDelete;
    }

    public JButton getBtnPrint() {
	if (btnPrint == null) {
	    btnPrint = new JButton("PRINT");
	    btnPrint.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    btnPrintActionPerformed(e);
		}
	    });
	    btnPrint.setForeground(Color.WHITE);
	    btnPrint.setFont(new Font("Tahoma", Font.BOLD, 43));
	    btnPrint.setBackground(Color.RED);
	    btnPrint.setBounds(1126, 547, 290, 78);
	}
	return btnPrint;
    }

    public JLabel getLblSatellite() {
	if (lblSatellite == null) {
	    lblSatellite = new JLabel("Satellite");
	    lblSatellite.setHorizontalAlignment(SwingConstants.TRAILING);
	    lblSatellite.setBounds(1364, 642, 68, 16);
	}
	return lblSatellite;
    }

    public String getGender() {
	if (rdbtnF.isSelected()) return "F";
	return "M";
    }

    protected void btnAddActionPerformed(ActionEvent arg0) {
	try {
	    String Sno = textField.getText();
	    String Sname = textField_1.getText();
	    String Sgender = getGender();
	    String Sclass = textField_2.getText();
	    String Sadress = textField_3.getText();
	    String Semail = textField_4.getText();

	    // Kiểm tra dữ liệu nhập vào, kiểm tra trùng khóa chính trong database
	    if (manager.checkStudent(Sno)) throw new Exception("This student ID is already exits in database!");
	    if (!Validation.checkIdFormat(Sno)) throw new Exception("Id of student can not contain space!");
	    if (!Validation.checkNameFormat(Sname)) throw new Exception("Name of student can not contain special(s) character!");
	    if (!Validation.checkIdFormat(Sclass)) throw new Exception("Name of class can not contain special(s) character!");
	    if (!Validation.checkNameFormat(Sadress)) throw new Exception("Adress can not contain special(s) character!");

	    // Thêm dữ liệu vào database
	    manager.addNew(Sno, Sname, Sgender, Sclass, Sadress, Semail);

	    // Cập nhật hiển thị database cho phần mềm
	    data = manager.getAll();
	    ((DefaultTableModel) (table.getModel())).setDataVector(data, header);

	    // Thông báo thành công
	    JOptionPane.showMessageDialog(contentPane, "Add Success!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
	} catch (Exception e1) {
	    JOptionPane.showMessageDialog(contentPane, "Add new failure\nDetails: " + e1, "Error", JOptionPane.ERROR_MESSAGE);
	}
    }

    protected void btnDeleteActionPerformed(ActionEvent e) {
	try {
	    String Sno = textField.getText();

	    // Kiểm tra sinh viên có trong database hay không
	    if (!manager.checkStudent(Sno)) throw new Exception("This student ID is not exits in database, So can delete!");
	    manager.delete(Sno);

	    // Cập nhật lại dữ liệu hiển thị trên phần mềm
	    data = manager.getAll();
	    ((DefaultTableModel) (table.getModel())).setDataVector(data, header);

	    // Thông báo xóa thành công
	    JOptionPane.showMessageDialog(contentPane, "Delete Success!", "Sucess", JOptionPane.INFORMATION_MESSAGE);
	} catch (Exception e1) {
	    JOptionPane.showMessageDialog(contentPane, "Delete failure\nDetails:" + e1, "Error", JOptionPane.ERROR_MESSAGE);
	}
    }

    protected void btnPrintActionPerformed(ActionEvent e) {
	try {
	    // Kiểm tra database trước khi in
	    if (data.isEmpty()) throw new Exception("Make sure your load the database in order to print!");

	    MessageFormat header = new MessageFormat("University");
	    MessageFormat footer = new MessageFormat("");
	    table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
	} catch (Exception e1) {
	    JOptionPane.showMessageDialog(contentPane, "Print failure\nDetails: " + e1);
	}
    }

    protected void rdbtnMActionPerformed(ActionEvent arg0) {
	if (rdbtnM.isSelected()) rdbtnF.setSelected(false);
    }

    protected void rdbtnFActionPerformed(ActionEvent e) {
	if (rdbtnF.isSelected()) rdbtnM.setSelected(false);
    }
}
