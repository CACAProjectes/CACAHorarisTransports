package es.xuan.horaristransp;

public class CalcularHores {

	public static void main(String[] args) {
		System.out.println("Dif. temps: " + calcularDifHores("18:05","17:58"));	// -7

		System.out.println("Dif. temps: " + calcularDifHores("17:15","17:20"));	// 5
		System.out.println("Dif. temps: " + calcularDifHores("17:15","18:30"));	// 75
		System.out.println("Dif. temps: " + calcularDifHores("17:15","17:15"));	// 0
		System.out.println("Dif. temps: " + calcularDifHores("17:15","19:15"));	// 120
		
		System.out.println("Dif. temps: " + calcularDifHores("17:15","17:10"));	// -5
		System.out.println("Dif. temps: " + calcularDifHores("17:15","17:06"));	// -9
		System.out.println("Dif. temps: " + calcularDifHores("17:15","17:00"));	// 0
		System.out.println("Dif. temps: " + calcularDifHores("17:15","16:00"));	// 0
		System.out.println("Dif. temps: " + calcularDifHores("17:15","18:00"));	// 45
		System.out.println("Dif. temps: " + calcularDifHores("17:55","18:00"));	// 5
	}
	private static int calcularDifHores(String pHora, String pHoraDif) {
		String[] iHora = pHora.split(":");
		String[] iHoraDif = pHoraDif.split(":");
		int iRes = (Integer.parseInt(iHoraDif[0]) * 60 + Integer.parseInt(iHoraDif[1])) -
				(Integer.parseInt(iHora[0]) * 60 + Integer.parseInt(iHora[1]));
		return (iRes < -9 ? 0 : iRes);
	}
	private static int calcularDifHores2(String pHora, String pHoraDif) {
		String[] iHora = pHora.split(":");
		String[] iHoraDif = pHoraDif.split(":");
		// Minuts major		
		if (Integer.parseInt(iHora[1]) < Integer.parseInt(iHoraDif[1])) {
			// Comparar minuts
			if (Integer.parseInt(iHora[0]) == Integer.parseInt(iHoraDif[0])) {
				// Comparar hores
				// "17:15","17:10"
				return Integer.parseInt(iHoraDif[1]) - Integer.parseInt(iHora[1]);
			}
			if (Integer.parseInt(iHora[0]) > Integer.parseInt(iHoraDif[0])) {
				// Comparar hores
				// "18:15","17:10"
				int iRes = -((Integer.parseInt(iHora[0]) - Integer.parseInt(iHoraDif[0]) - 1) * 60 +
						(60 - Integer.parseInt(iHoraDif[1])) +
						Integer.parseInt(iHora[1])); 
				return (iRes < -9 ? 0 : iRes);
			}
			if (Integer.parseInt(iHora[0]) < Integer.parseInt(iHoraDif[0])) {
				// Comparar hores
				// "16:15","17:10"	-> 55m
				// "15:15","17:10"	-> 45 + 60 + 10 -> 115m
				int iRes = ((Integer.parseInt(iHoraDif[0]) - Integer.parseInt(iHora[0]) - 1) * 60 +
						(60 - Integer.parseInt(iHora[1])) +
						Integer.parseInt(iHoraDif[1])); 
				return (iRes < -9 ? 0 : iRes);	// Només si es menor a 10 minuts
			}
		}
		// Minuts iguals
		if (Integer.parseInt(iHora[1]) == Integer.parseInt(iHoraDif[1])) {
			// Comparar minuts
			if (Integer.parseInt(iHora[0]) >= Integer.parseInt(iHoraDif[0])) {
				// Comparar hores
				// "17:15","17:15"
				// "16:15","17:15"	-> 60m
				// "15:15","17:15"	-> 120m
				return 0;
			}
			if (Integer.parseInt(iHora[0]) < Integer.parseInt(iHoraDif[0])) {
				// Comparar hores
				// "18:15","17:15"
				return (Integer.parseInt(iHoraDif[0]) - Integer.parseInt(iHora[0])) * 60;
			}
		}
		// Minuts menor
		if (Integer.parseInt(iHora[1]) > Integer.parseInt(iHoraDif[1])) {
			// Comparar minuts
			if (Integer.parseInt(iHora[0]) == Integer.parseInt(iHoraDif[0])) {
				// Comparar hores
				// "17:15","17:10"
				int iRes = Integer.parseInt(iHoraDif[1]) - Integer.parseInt(iHora[1]); 
				return (iRes < -9 ? 0 : iRes);	// Només si es menor a 10 minuts 
			}
			if (Integer.parseInt(iHora[0]) < Integer.parseInt(iHoraDif[0])) {
				// Comparar hores
				// "16:15","17:10"	-> 55m
				// "15:15","17:10"	-> 45 + 60 + 10 -> 115m
				int iRes = ((Integer.parseInt(iHoraDif[0]) - Integer.parseInt(iHora[0]) - 1) * 60 +
						(60 - Integer.parseInt(iHora[1])) +
						Integer.parseInt(iHoraDif[1])); 
				return (iRes < -9 ? 0 : iRes);	// Només si es menor a 10 minuts
			}
			if (Integer.parseInt(iHora[0]) > Integer.parseInt(iHoraDif[0])) {
				return 0;	// Només si es menor a 10 minuts
			}	
		}	
		return 0;
	}
}
