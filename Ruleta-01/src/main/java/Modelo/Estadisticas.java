package Modelo;

import java.util.*;

public class Estadisticas {

	Collection<Resultado> resultados;

	private int totalJugadas;
	private int victorias;
	private double porcentajeVictorias;
	private int rachaMaxima;
	private TipoApuesta tipoMasJugado;

	private void calcularEstadisticas() {

		totalJugadas = resultados.size();
		victorias = 0;
		porcentajeVictorias = 0;
		rachaMaxima = 0;
		tipoMasJugado = null;

		int rachaActual = 0;

		Map<TipoApuesta, Integer> frecuencia =
				new EnumMap<>(TipoApuesta.class);

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

			TipoApuesta tipo = resultado.getTipoApuesta();

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

		for (TipoApuesta tipo: TipoApuesta.values()) {

			int cantidad = frecuencia.getOrDefault(tipo, 0);

			if (cantidad > mayorFrecuencia) {
				mayorFrecuencia = cantidad;
				tipoMasJugado = tipo;
			}
		}
	}

	public Estadisticas(List<Resultado> resultados) {
		this.resultados = new ArrayList<>(resultados);
		calcularEstadisticas();
	}

	public int getTotalJugadas() {
		return this.totalJugadas;
	}

	public int getVictorias() {
		return this.victorias;
	}

	public double getPorcentajeVictorias() {
		return this.porcentajeVictorias;
	}

	public int getRachaMaxima() {
		return this.rachaMaxima;
	}

	public TipoApuesta getTipoMasJugado() {
		return this.tipoMasJugado;
	}

}