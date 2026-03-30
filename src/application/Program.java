package application;

import entities.Evento;
import entities.Eventos;
import entitiesException.DomainException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Program {
    static Scanner sc = new Scanner(System.in);
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    static Eventos evento = new Eventos();

    public static void main(String[] args) {

        menu();

    }

    public static void menu() {
        try {
            int option;
            System.out.println("------------------------------------");
            System.out.println(
                    """
                            1 - Adicionar evento
                            \
                            2 - Listar eventos
                            \
                            3 - Editar evento
                            \
                            4 - Remover evento
                            \
                            0 - Sair"""
            );
            System.out.print("Escolha uma opção: ");
            option = sc.nextInt();
            if (option == 1) {
                addEvento();
            }
            else if(option == 2){
                listEvento();
            }
            else if (option == 3){
                editarEvento();
            }
            else if (option == 4){
                remove();
            }
            else if (option == 0) {
                System.out.println("\033[36mPrograma encerrado. Volte sempre!\033[m");
            }
        } catch (RuntimeException e) {
            System.out.println("\033[31mErro inesperado.\033[m");
        }
    }

    public static void addEvento(){

        try{
            System.out.print("Quantos eventos você irá cadastrar? ");
            int n = sc.nextInt();
            sc.nextLine();

            for (int i=0; i<n; i++){
                System.out.println("------------------------------------");
                System.out.println("Evento #" + (i+1) + ": ");
                System.out.print("Nome: ");
                String nome = sc.nextLine().toUpperCase();
                System.out.print("Data (DD/MM/AAAA): ");
                Date data = sdf.parse(sc.nextLine());
                System.out.print("Horário: ");
                String hora = sc.next();
                sc.nextLine();
                System.out.print("Local: ");
                String local = sc.nextLine().toUpperCase();
                System.out.print("Responsável: ");
                String responsavel = sc.nextLine().toUpperCase();
                Evento dado = new Evento(null, nome, data, hora, local, responsavel);
                evento.addEvento(dado);
            }
            menu();
        }
        catch (ParseException e){
            System.out.println("\033[31mFormato de data inválido.\033[m");
        }
        catch (DomainException e){
            System.out.println("Erro de data: " + e.getMessage());
        }
        catch (RuntimeException e){
            System.out.println("\033[31mErro inesperado.\033[m");
        }
    }

    public static void listEvento() {
        try{
            System.out.println("Eventos: ");
            evento.listarTudo();
            menu();
        } catch (RuntimeException e) {
            System.out.println("\033[31mErro inesperado.\033[m");
        }
    }


    public static void editarEvento(){
        while (true){
            try{
                System.out.println("------------------------------------");
                evento.listarTudo();
                System.out.print("Digite o id do evento que será editado: ");
                String id = sc.next();
                Evento obj = evento.encontrarId(Integer.parseInt(id));
                sc.nextLine();
                System.out.print("Nome: ");
                String nome = sc.nextLine().toUpperCase();
                obj.setNome(nome);
                System.out.print("Data (DD/MM/AAAA): ");
                Date data = sdf.parse(sc.nextLine());
                obj.setData(data);
                System.out.print("Horário: ");
                String hora = sc.nextLine();
                obj.setHora(hora);
                System.out.print("Local: ");
                String local = sc.nextLine().toUpperCase();
                obj.setLocal(local);
                System.out.print("Responsável: ");
                String responsavel = sc.nextLine().toUpperCase();
                obj.setResponsavel(responsavel);
                evento.editarEvento(obj);
                menu();
            }catch (ParseException e){
                System.out.println("\033[31mFormato de data inválido.\033[m");
            }
            catch (DomainException e){
                System.out.println(e.getMessage());
            }
            catch (RuntimeException e) {
                System.out.println("\033[31mErro inesperado.\033[m");
            }
        }
    }


    public static void remove() {
        while (true){
            try{
                System.out.println("------------------------------------");
                evento.listarTudo();
                System.out.print("Digite o id do evento que será excluído: ");
                String id = sc.next();
                Evento obj = evento.encontrarId(Integer.parseInt(id));
                evento.removeEvento(obj.getId());
                menu();
            }
            catch (DomainException e){
                System.out.println(e.getMessage());
            }
            catch (RuntimeException e) {
                System.out.println("\033[31mErro inesperado.\033[m");
            }
        }
    }
}