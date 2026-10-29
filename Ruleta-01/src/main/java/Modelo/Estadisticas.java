package Modelo;

import java.util.*;

public class Estadisticas {

	private final Collection<Resultado> resultados;

	private int totalJugadas;
	private int victorias;
	private double porcentajeVictorias;
	private int rachaMaxima;
	private String tipoMasJugado;

	public Estadisticas(List<Resultado> resultados) {
		this.resultados = new ArrayList<>(resultados);
		calcularEstadisticas();
	}

	private void calcularEstadisticas() {

		totalJugadas = resultados.size();
		victorias = 0;
		porcentajeVictorias = 0;
		rachaMaxima = 0;
		tipoMasJugado = null;

		int rachaActual = 0;

		Map<String, Integer> frecuencia = new LinkedHashMap<>();

		for (Resultado resultado: resultados) {

			if (resultado.isGano()) {
				victorias++;
				rachaActual++;

				rachaMaxima = Math.max(
						rachaMaxima, rachaActual
				);
			} else {
				rachaActual = 0;
			}

			String tipo = resultado.getTipoApuesta()
					.getEtiqueta();

			frecuencia.put(
					tipo,
					frecuencia.getOrDefault(tipo, 0) + 1
			);
		}

		if (totalJugadas > 0) {
			porcentajeVictorias =
					(victorias * 100.0) / totalJugadas;
		}

		int mayorFrecuencia = 0;

		for (Map.Entry<String, Integer> entrada :
				frecuencia.entrySet()) {

			if (entrada.getValue() > mayorFrecuencia) {
				mayorFrecuencia = entrada.getValue();
				tipoMasJugado = entrada.getKey();
			}
		}
	}

	public int getTotalJugadas() {
		return totalJugadas;
	}

	public int getVictorias() {
		return victorias;
	}

	public double getPorcentajeVictorias() {
		return porcentajeVictorias;
	}

	public int getRachaMaxima() {
		return rachaMaxima;
	}

	public String getTipoMasJugado() {
		return tipoMasJugado;
	}
}