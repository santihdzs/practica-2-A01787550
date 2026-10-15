# Bitácora — Práctica 2 Sabores

Santiago Hernández · A01787550

## Ejercicio 0 · Clasifica

| # | Responsabilidad | Capa |
|---|---|---|
| 1 | Calcular el promedio de estrellas | Dominio |
| 2 | Mostrar 4.333… como "4.3" | UI |
| 3 | La lista de restaurantes de muestra | Datos |
| 4 | "El comentario debe tener al menos 15 caracteres" | Dominio |
| 5 | El botón "Publicar" se pinta gris | UI |
| 6 | Recordar cuántas estrellas lleva marcadas el usuario | ViewModel |
| 7 | Decidir a qué pantalla ir después de guardar | UI |
| 8 | Convertir `priceLevel = 2` en "$$" | Dominio |

## Ejercicio B2 · Rompe la navegación

1. **`StringType` sin tocar `getInt`:** no crashea. El argumento llega como `0` al leerlo en el destino (`getInt` sobre un String da el default). `getById(0)` regresa `null` y la pantalla de detalle sale en blanco.
2. **5 taps rápidos y 5 "atrás":** se apilan 5 copias del detalle en el back stack; hay que dar "atrás" 5 veces. Se arregla con `launchSingleTop = true`.

## Ejercicio B4 · Diagnostica

1. **¿Qué prueba lo detecta?** La de la rotación (la del ViewModel, "¿qué se ve AHORA?"): el estado debe sobrevivir y aquí no sobrevive.
2. **¿De quién son las reseñas?** De ninguno de los dos. La lista también necesita el promedio, así que deben vivir arriba de ambas pantallas (en un ViewModel compartido).
3. **`remember` en `SaboresApp()`:** arregla el Experimento 1 (navegar), porque `SaboresApp` no sale de la composición. No arregla el 2 (girar), porque la Activity se recrea y el `remember` muere con ella.
