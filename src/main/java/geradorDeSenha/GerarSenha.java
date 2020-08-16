package geradorDeSenha;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GerarSenha {

	private static String[] alfabeto = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
			"q", "r", "s", "t", "u", "v", "x", "w", "y", "z", "A", "B", "C", "D", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X", "W", "Y", "Z" };
	private static String[] numeros = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private static String[] caracterEspeciais = { ".", ",", "!", "-", "@","/", "?" };
	private static ArrayList<String[]> arrayListVetores = new ArrayList<String[]>();

	public static void embaralharVetores() {
		// vamos embaralhar mesmo que no próximo passo não precise só pra ficar um pouco
		// mais divertido.
		for (int i = 0; i < arrayListVetores.size(); i++) {
			String[] vetorAuxiliar = arrayListVetores.get(i);
			Random random = new Random();
			for (int n = 0; n < vetorAuxiliar.length - 1; n++) {
				int aux = random.nextInt(vetorAuxiliar.length);
				String auxiliar = vetorAuxiliar[n];
				vetorAuxiliar[n] = vetorAuxiliar[aux];
				vetorAuxiliar[aux] = auxiliar;
			}
			arrayListVetores.set(i, vetorAuxiliar);
		}
	}

	public static String gerarSenha(Integer[] quantNumeros) {
		String senha = "";
		Random random = new Random();
		for (int n = 0; n < quantNumeros.length; n++) {
			if (0 == n) {
				for (int i = 0; quantNumeros[n] > i; i++) {
					senha = senha + arrayListVetores.get(0)[random.nextInt(alfabeto.length)];
				}
			}
			if (1 == n) {
				for (int i = 0; quantNumeros[n] > i; i++) {
					senha = senha + arrayListVetores.get(1)[random.nextInt(numeros.length)];
				}
			}
			if (2 == n) {
				for (int i = 0; quantNumeros[n] > i; i++) {
					senha = senha + arrayListVetores.get(2)[random.nextInt(caracterEspeciais.length)];
				}
			}
		}

		return senha;

	}

	public static void main(String[] args) {
		
		arrayListVetores.add(alfabeto);
		arrayListVetores.add(numeros);
		arrayListVetores.add(caracterEspeciais);

		embaralharVetores();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Vamos gerar sua senha!");
		System.out.print("Quantas letras? ");
		int quantLetras = sc.nextInt();
		System.out.println("Quantos numeros? ");
		int quantNumeros = sc.nextInt();
		System.out.print("Quantos caracteres especiais? ");
		int quantCaracter = sc.nextInt();
		Integer[] numeros = { quantLetras, quantNumeros, quantCaracter };
		System.out.println("Sua senha :" + gerarSenha(numeros));
		sc.close();

	}

}
