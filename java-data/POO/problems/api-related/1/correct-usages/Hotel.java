import java.lang.StringBuffer;

public class Hotel {
  
  public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("Código: ");buf.append(this.getCod());buf.append("\n");
      buf.append("Nome: ");buf.append(this.getNome());buf.append("\n");
      buf.append("Local: ");buf.append(this.getLocal());buf.append("\n");
      buf.append("Categoria: ");buf.append(this.getCategoria());buf.append("\n");
      buf.append("Quartos disp: ");buf.append(this.getDisponiveis());buf.append("\n");
      buf.append("Preço: ");buf.append(this.getPreco());buf.append("\n");
      return buf.toString();
    }
