package hrs_re_2;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
class CustomTable extends JTable{
	CustomTable(DefaultTableModel tableModel) {
		super(tableModel);
		setFont(new Font("Verdana", Font.PLAIN, 20));
		setRowHeight(40);
		setSelectionForeground(Color.decode("#ab782b"));
		setFocusable(false);
		getTableHeader().setBackground(Color.decode("#ab782b"));
		getTableHeader().setFont(new Font("Verdana", Font.BOLD, 20));
		getTableHeader().setForeground(Color.WHITE);
		getTableHeader().setPreferredSize(new Dimension(0, 55));
		setShowGrid(false);
		setSelectionBackground(Color.WHITE);
		setDefaultEditor(Object.class, null);
		setDefaultRenderer(Object.class, new TableRenderer());
	}
	
	static void setScrollPaneProperties(JScrollPane scrollPane) {
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
		scrollPane.setPreferredSize(new Dimension(800, 300)); //500
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBackground(Color.WHITE);
	}
	
	class TableRenderer extends DefaultTableCellRenderer {
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	    {
	        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        c.setBackground(Color.decode("#f4e9d4"));
	        return c;
	    }
	    

	}
}
