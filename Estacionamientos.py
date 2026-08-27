import json
import os
import re
from datetime import datetime    

class Vehiculo:
    def __init__(self, patente, marca, modelo, color):
        self.patente = patente
        self.marca = marca
        self.modelo = modelo
        self.color = color
        self.hora_ingreso = datetime.now()

    def mostrar_info(self):
         print(f"Patente: {self.patente}")
         print(f"Marca: {self.marca}")
         print(f"Modelo: {self.modelo}")
         print(f"Color: {self.color}")
         print(f"Hora del ingreso: {self.hora_ingreso.strftime('%d/%m/%Y %H:%M:%S')}")
         
    def to_dict(self):
         return {
              "patente": self.patente,
              "marca": self.marca,
              "modelo": self.modelo,
              "color": self.color,
              "hora_ingreso": self.hora_ingreso.strftime('%d/%m/%Y %H:%M:%S')
         }

class Espacio:
    def __init__(self, numero):
          self.numero = numero
          self.ocupado = False
          self.vehiculo = None

    def ocupar(self, vehiculo):
        self.vehiculo = vehiculo
        self.ocupado = True

    def liberar(self):
        self.vehiculo = None
        self.ocupado = False
        
    def espacio_libre(self):
        return not self.ocupado
    
  

class Estacionamiento:
     def __init__(self, nombre, capacidad):
          self.nombre = nombre
          self.capacidad = capacidad
          self.espacios = []
          self.vehiculos = []

          for i in range(1, capacidad + 1):
              self.espacios.append(Espacio(i))
     

     def validar_patente(self, patente):
         patente = patente.upper()

         patente_antigua = r"^[A-Z]{2}[0-9]{4}$"
         patente_nueva = r"^[A-Z]{4}[0-9]{2}$"
         patente_moto_antigua = r"^[A-Z]{3}[0-9]{2}$"
         patente_moto_nueva = r"^[A-Z]{4}[0-9]{1}$"

         return (re.match(patente_antigua, patente) or 
                 re.match(patente_nueva, patente) or 
                 re.match(patente_moto_antigua, patente) or
                 re.match(patente_moto_nueva, patente)
                 )
         
     def registrar_ingreso(self):
         
         if not any(espacio.espacio_libre() for espacio in self.espacios):
             print("No hay espacios disponibles. ")
             return
         
         patente = input("Ingrese la patente del vehiculo: ").upper()
         patente = patente.replace("-", "").replace(" ", "")

         if not self.validar_patente(patente):
             print("La patente ingresada no es válida. ")
             return
         
         for vehiculo in self.vehiculos:
             if vehiculo.patente == patente:
                 print("El vehiculo ya esta registrado. ")
                 return
         
         marca = input("Ingrese la marca: ").upper()
         modelo = input("Ingrese el modelo: ")
         color = input("Ingrese el color del vehiculo: ")
         
         vehiculo = Vehiculo(
             patente,
             marca,
             modelo,
             color
         )

         for espacio in self.espacios:
            if espacio.espacio_libre():
                espacio.ocupar(vehiculo)
                self.vehiculos.append(vehiculo)

                print("Vehiculo registrado correctamente.")
                print("Espacio asignado:", espacio.numero)
                return
        
         
     def registrar_salida(self):
         
         patente = input("Ingrese la patente del vehiculo: ").upper()
         patente = patente.replace("-", "").replace(" ", "")

         if not self.validar_patente(patente):
             print("La patente ingreseda no es válida. ")
             return

         for vehiculo in self.vehiculos:

            if vehiculo.patente == patente:
            
                for espacio in self.espacios:
                        if espacio.vehiculo == vehiculo:
                            espacio.liberar()
                            break
                
                self.vehiculos.remove(vehiculo)


                print("Vehiculo retirado correctamente")
                print("Patente:", vehiculo.patente)
                print("Hora de salida: ", datetime.now().strftime("%d/%m/%Y %H:%M:%S"))
                return
            
         print("No se encontro un vehiculo con esta patente")
     
     def buscar_vehiculo(self, patente):
          
          patente = patente.upper().replace("-", "").replace(" ", "")

          if not self.validar_patente(patente):
             print("La patente ingreseda no es válida. ")
             return

          for espacio in self.espacios:
               if espacio.ocupado and espacio.vehiculo.patente == patente:
                    print("\nVehiculo encontrado")
                    print(f"Espacio: {espacio.numero}")
                    espacio.vehiculo.mostrar_info()
                    return
               
          print("\nEl vehiculo no fue encontrado.")
     
     def mostrar_vehiculos(self):
          print("\n--- Vehiculos Estacionados ---")

          usado = False

          for espacio in self.espacios:
               if espacio.ocupado:
                    usado = True
                    print(f"\nEspacio {espacio.numero}")
                    espacio.vehiculo.mostrar_info()


          if not usado:
             print("No hay ningún vehiculo estacionado actualmente.")
        
     def mostrar_espacios(self):
        libres = 0
        ocupados = 0

        for espacio in self.espacios:
            if espacio.ocupado:
                ocupados += 1
            else:
                libres += 1

        print(f"Espacios ocupados: {ocupados}")
        print(f"Espacios libres: {libres}")
                  
     def guardar_datos(self):
          
          datos = []

          for espacio in self.espacios:
            if espacio.ocupado:

                datos.append({
                    "espacio": espacio.numero,
                    **espacio.vehiculo.to_dict()
                })
                
          with open("vehiculos.json", "w", encoding="utf-8") as archivo:
              json.dump(datos, archivo, indent=4, ensure_ascii=False)
           
          print("Datos guardados correctamente")
     
     def cargar_datos(self):
          if not os.path.exists("vehiculos.json"):
               print("No se encontraron datos guardados.")
               return
          
          self.vehiculos = []

          self.espacios = []

          for i in range(1, self.capacidad + 1):
              self.espacios.append(Espacio(i))
        
          try:
             with open("vehiculos.json", "r", encoding="utf-8") as archivo:
                 datos = json.load(archivo)
          
          except json.JSONDecodeError:
              print("El archivo de datos esta vacio. ")
              return

          for dato in datos:
               vehiculo = Vehiculo(
                   patente = dato["patente"],
                   marca = dato["marca"],
                   modelo = dato["modelo"],
                   color = dato["color"]
            )

               vehiculo.hora_ingreso = datetime.strptime(
                   dato["hora_ingreso"],
                   "%d/%m/%Y %H:%M:%S"
            )
               
               self.vehiculos.append(vehiculo)

               espacio = self.espacios[dato["espacio"] - 1]
               espacio.ocupar(vehiculo)
               
          print("Datos cargados correctamente")
        

                     

def main():

    estacionamiento = Estacionamiento("Estacionamiento UFRO", 10)

    estacionamiento.cargar_datos()

    while True:
        print("Elige una opcion")
        print("1- Registrar ingreso")
        print("2- Registrar salida")
        print("3- Mostrar vehiculo")
        print("4- Mostrar vehiculos estacionados")
        print("5- Mostrar espacios disponibles")
        print("6- Guardar datos")
        print("7- Cargar datos")
        print("8- Salir")

        try:
            opcion = int(input("Seleccione una opcion: "))
        except ValueError:
            print("Debe ingresar un numero del 1 al 8. Intentelo nuevamente. ")
            continue

        if opcion == 1:
            estacionamiento.registrar_ingreso()
        elif opcion == 2:
            estacionamiento.registrar_salida()
        elif opcion == 3:
            patente = input("Ingrese la patente que desea buscar: ")
            estacionamiento.buscar_vehiculo(patente)
        elif opcion == 4:
            estacionamiento.mostrar_vehiculos()

        elif opcion == 5:
            estacionamiento.mostrar_espacios()

        elif opcion == 6:
            estacionamiento.guardar_datos()

        elif opcion == 7:
            estacionamiento.cargar_datos()
        
        elif opcion == 8:
            estacionamiento.guardar_datos()
            print("Programa finalizado")
            break
        else:
            print("La opción que ingreso no es válida. ")

main()



