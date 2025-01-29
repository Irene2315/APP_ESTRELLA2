package com.example.front_android;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.front_android.Modelos.FavoritoCamara;
import com.example.front_android.Modelos.FavoritoIncidencia;
import com.example.front_android.PETICIONES_API.PeticionesCamaras.ObtenerCamarasRegion;

import com.example.front_android.Adaptadores.WindowAdapterUniversal;
import com.example.front_android.Modelos.Camara;
import com.example.front_android.Modelos.Ciudad;
import com.example.front_android.Modelos.Incidencia;
import com.example.front_android.Modelos.Provincia;
import com.example.front_android.Modelos.Region;
import com.example.front_android.Modelos.TipoIncidencia;
import com.example.front_android.PETICIONES_API.PeticionesCamaras;
import com.example.front_android.PETICIONES_API.PeticionesCiudades;
import com.example.front_android.PETICIONES_API.PeticionesIncidencias;
import com.example.front_android.PETICIONES_API.PeticionesProvincias;
import com.example.front_android.PETICIONES_API.PeticionesRegiones;
import com.example.front_android.PETICIONES_API.PeticionesTiposDeIncidencia;
import com.example.front_android.bdd.GestorBDD;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private FusedLocationProviderClient fs;
    private List<Incidencia> miListaIncidencias = new ArrayList<>();
    private List<Camara> miListaCamaras = new ArrayList<>();
    private Spinner selectRegion;
    private Spinner selectProvincia;
    private Spinner selectCiudad;
    private Spinner selectTipoIncidencia;
    private ArrayList<String> miListaRegiones = new ArrayList<>();
    private ArrayList<String> miListaProvincias = new ArrayList<>();
    private ArrayList<String> miListaCiudades = new ArrayList<>();
    private ArrayList<String> miListaTipoIncidencias = new ArrayList<>();
    private List<Region> regiones = new ArrayList<>();
    private List<Ciudad> ciudades= new ArrayList<>();
    private List<TipoIncidencia> tipoIncidencias= new ArrayList<>();
    private List<Provincia> provincias= new ArrayList<>();
    private Switch incidencias_switch;
    private Switch camaras_switch;
    private Switch favoritos_switch;
    final List<Camara>[] camarasResultado = new List[1];
    final List<Incidencia>[] incidenciasResultado = new List[1];
    private List<Marker> markersList = new ArrayList<>();

    private static WindowAdapterUniversal adapterUniversal;
    private GestorBDD gestorBDD;
    private List<FavoritoIncidencia> miListaFavoritosIncidencias = new ArrayList<>();
    private List<FavoritoCamara> miListaFavoritosCamaras = new ArrayList<>();

    private List<Incidencia> incidencias_fav = new ArrayList<>();
    private List<Camara> camaras_fav = new ArrayList<>();

    public MapaFragment() {
        // Constructor requerido vacío
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferences = getContext().getSharedPreferences("app_localDatos", MODE_PRIVATE);
        boolean session = preferences.getBoolean("session", false);

        if (!session) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        gestorBDD = new GestorBDD(this.getContext());
        gestorBDD.conectar();

        miListaFavoritosIncidencias = gestorBDD.getGestorIncidenciasFavoritas().seleccionarTodasLasIncidenciasFavoritas();

        miListaFavoritosCamaras = gestorBDD.getGestorCamarasFavoritas().seleccionarTodasLasCamarasFavoritas();


        fs = LocationServices.getFusedLocationProviderClient(getContext());
        selectRegion = view.findViewById(R.id.spinnerRegion);
        selectProvincia = view.findViewById(R.id.spinnerProvincia);
        selectCiudad = view.findViewById(R.id.spinnerCiudad);
        selectTipoIncidencia = view.findViewById(R.id.spinnerTipoIncidencia);
        incidencias_switch = view.findViewById(R.id.switch_incidencias);
        camaras_switch = view.findViewById(R.id.switch_camaras);
        favoritos_switch = view.findViewById(R.id.switch_fevoritos);

        incidencias_switch.setChecked(true);
        camaras_switch.setChecked(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(getContext(), "Error al cargar el mapa", Toast.LENGTH_SHORT).show();
        }

        setPermisosGeoloc();

        SharedPreferences preferences = getContext().getSharedPreferences("app_localDatos", MODE_PRIVATE);
        boolean logueado = preferences.getBoolean("session", false);

        if (!logueado) {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }

        selectRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("MapaFragment", "Region seleccionada: " + position);

                selectCiudad.setSelection(0);
                selectTipoIncidencia.setSelection(0);
                selectProvincia.setSelection(0);

                if (regiones != null && !regiones.isEmpty() && position > 0) {
                    Region regionSeleccionada = regiones.get(position - 1);

                    int regionId = regionSeleccionada.getIdRegion();

                    camaras_switch.setEnabled(true);
                    camaras_switch.setChecked(true);

                    new ObtenerCamarasRegion() {
                        @Override
                        protected void onPostExecute(List<Camara> camaras) {
                            super.onPostExecute(camaras);
                            Log.d("MapaFragment", "Cámaras: " + (camaras != null ? camaras.size() : 0));
                            camarasResultado[0] = camaras;

                            if (incidenciasResultado[0] != null) {
                                actualizarMapa(camaras, incidenciasResultado[0]);
                            }
                        }
                    }.execute(regionId);

                    new PeticionesIncidencias.ObtenerIncidenciasRegion() {
                        @Override
                        protected void onPostExecute(List<Incidencia> incidencias) {
                            super.onPostExecute(incidencias);
                            Log.d("MapaFragment", "Incidencias: " + (incidencias != null ? incidencias.size() : 0));
                            incidenciasResultado[0] = incidencias;

                            if (camarasResultado[0] != null) {
                                actualizarMapa(camarasResultado[0], incidencias);
                            }
                        }
                    }.execute(regionId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        selectProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (provincias != null && !provincias.isEmpty() && position > 0) {
                    Provincia provinciaSelected = provincias.get(position - 1);
                    int provinciaId = provinciaSelected.getId();

                    selectCiudad.setSelection(0);
                    selectTipoIncidencia.setSelection(0);
                    selectRegion.setSelection(0);

                    camaras_switch.setEnabled(false);
                    camaras_switch.setChecked(false);

                    new PeticionesIncidencias.ObtenerIncidenciasProvincia() {
                        @Override
                        protected void onPostExecute(List<Incidencia> incidencias) {
                            super.onPostExecute(incidencias);
                            incidenciasResultado[0] = incidencias;

                            if (incidenciasResultado[0] != null) {
                                actualizarMapa(null, incidencias);
                            }
                        }
                    }.execute(provinciaId);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        selectCiudad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (ciudades != null && !ciudades.isEmpty() && position > 0) {
                    Ciudad ciudadSelected = ciudades.get(position - 1);
                    int ciudadId = ciudadSelected.getId();

                    selectProvincia.setSelection(0);
                    selectTipoIncidencia.setSelection(0);
                    selectRegion.setSelection(0);

                    camaras_switch.setEnabled(false);
                    camaras_switch.setChecked(false);

                    new PeticionesIncidencias.ObtenerIncidenciasCiudad() {
                        @Override
                        protected void onPostExecute(List<Incidencia> incidencias) {
                            super.onPostExecute(incidencias);
                            Log.d("MapaFragment", "Incidencias en la ciudad: " + (incidencias != null ? incidencias.size() : 0));
                            incidenciasResultado[0] = incidencias;

                            if (incidenciasResultado[0] != null) {
                                actualizarMapa(null, incidencias);
                            }
                        }
                    }.execute(ciudadId);
                    Log.d("MapaFragment", "ID de ciudad pasada a ObtenerIncidenciasCiudad: " + ciudadId);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        selectTipoIncidencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (tipoIncidencias != null && !tipoIncidencias.isEmpty() && position > 0) {
                    TipoIncidencia tipoSelected = tipoIncidencias.get(position - 1);
                    int tipoId = tipoSelected.getId();

                    selectProvincia.setSelection(0);
                    selectCiudad.setSelection(0);
                    selectRegion.setSelection(0);

                    camaras_switch.setEnabled(false);
                    camaras_switch.setChecked(false);

                    new PeticionesIncidencias.ObtenerIncidenciasTipoIncidencia() {
                        @Override
                        protected void onPostExecute(List<Incidencia> incidencias) {
                            super.onPostExecute(incidencias);
                            incidenciasResultado[0] = incidencias;

                            if (incidenciasResultado[0] != null) {
                                actualizarMapa(null, incidencias);
                            }
                        }
                    }.execute(tipoId);
                    Log.d("MapaFragment", "ID de tipo pasada a ObtenerIncidenciasTipo: " + tipoId);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        incidencias_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (incidencias_switch.isChecked() && camaras_switch.isChecked()) {
                    actualizarMapa(camarasResultado[0], incidenciasResultado[0]);
                } else if (incidencias_switch.isChecked()) {
                    actualizarMapa(null, incidenciasResultado[0]);
                } else if (camaras_switch.isChecked()) {
                    actualizarMapa(camarasResultado[0], null);
                } else {
                    actualizarMapa(null, null);
                }
            }
        });

        camaras_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (incidencias_switch.isChecked() && camaras_switch.isChecked()) {
                    actualizarMapa(camarasResultado[0], incidenciasResultado[0]);
                } else if (camaras_switch.isChecked()) {
                    actualizarMapa(camarasResultado[0], null);
                } else if (incidencias_switch.isChecked()) {
                    actualizarMapa(null, incidenciasResultado[0]);
                } else {
                    actualizarMapa(null, null);
                }
            }
        });

        favoritos_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                for (Incidencia incidencia : miListaIncidencias) {

                    boolean esFavorito = false;
                    for (FavoritoIncidencia favorito : miListaFavoritosIncidencias) {
                        if (incidencia.getId() == favorito.getIdIncidencia()) {
                            esFavorito = true;
                            break;
                        }
                    }
                    if (esFavorito) {
                        incidencias_fav.add(incidencia);
                    }
                }

                for (Camara camara : miListaCamaras) {

                    boolean esFavorito = false;
                    for (FavoritoCamara favorito : miListaFavoritosCamaras) {
                        if (camara.getId() == favorito.getIdCamara()) {
                            esFavorito = true;
                            break;
                        }
                    }
                    if (esFavorito) {
                        camaras_fav.add(camara);
                    }
                }

                actualizarMapa(camaras_fav, incidencias_fav);
            }
        });


        ArrayAdapter<String> adapterRegion = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, miListaRegiones);
        adapterRegion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectRegion.setAdapter(adapterRegion);

        ArrayAdapter<String> adapterCiudad = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, miListaCiudades);
        adapterCiudad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCiudad.setAdapter(adapterCiudad);

        ArrayAdapter<String> adapterProvincia = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, miListaProvincias);
        adapterProvincia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectProvincia.setAdapter(adapterProvincia);



        ArrayAdapter<String> adapterTipoIncidencia = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, miListaTipoIncidencias);
        adapterTipoIncidencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectTipoIncidencia.setAdapter(adapterTipoIncidencia);




        new PeticionesRegiones.ObtenerTodasLasRegiones() {

            @Override
            protected void onPostExecute(List<Region> regiones) {
                super.onPostExecute(regiones);

                if (regiones != null && !regiones.isEmpty()) {
                    Log.d("MapaFragment", "Regiones obtenidas: " + regiones.size());
                    MapaFragment.this.regiones = regiones;

                    miListaRegiones.clear();
                    miListaRegiones.add("Regiones");

                    for (Region region : regiones) {
                        miListaRegiones.add(region.getNombreEs());
                    }

                    adapterRegion.notifyDataSetChanged();
                }
            }
        }.execute();


        if (!ciudades.isEmpty()){
            ciudades.clear();
        }
        ciudades.clear();
        new PeticionesCiudades.ObtenerTodasLasCiudades() {
            @Override
            protected void onPostExecute(List<Ciudad> ciudades) {
                super.onPostExecute(ciudades);

                if (ciudades != null && !ciudades.isEmpty()) {
                    MapaFragment.this.ciudades = ciudades;

                    miListaCiudades.clear();
                    miListaCiudades.add("Ciudades");
                    for (Ciudad ciudad : ciudades) {
                        miListaCiudades.add(ciudad.getNombre());

                    }

                    adapterCiudad.notifyDataSetChanged();
                }
            }
        }.execute();

        if (!provincias.isEmpty()){
            provincias.clear();
        }
        new PeticionesProvincias.ObtenerTodasLasProvincias() {
            @Override
            protected void onPostExecute(List<Provincia> provincias) {
                super.onPostExecute(provincias);

                if (provincias != null && !provincias.isEmpty()) {
                    MapaFragment.this.provincias = provincias;

                    miListaProvincias.clear();
                    miListaProvincias.add("Provincias");
                    for (Provincia provincia : provincias) {
                        miListaProvincias.add(provincia.getNombre());

                    }
                    adapterProvincia.notifyDataSetChanged();
                }
            }
        }.execute();

        if (!tipoIncidencias.isEmpty()){
            tipoIncidencias.clear();
        }
        new PeticionesTiposDeIncidencia.ObtenerTodasLosTiposDeIncidencia() {
            @Override
            protected void onPostExecute(List<TipoIncidencia> tipoIncidencias) {
                super.onPostExecute(tipoIncidencias);

                if (tipoIncidencias != null && !tipoIncidencias.isEmpty()) {
                    MapaFragment.this.tipoIncidencias = tipoIncidencias;

                    miListaTipoIncidencias.clear();
                    miListaTipoIncidencias.add("Tipo de Incidencias");
                    for (TipoIncidencia t : tipoIncidencias) {
                        miListaTipoIncidencias.add(t.getNombre());

                    }
                    adapterTipoIncidencia.notifyDataSetChanged();
                } else {
                    Log.d("Región", "No hay ciudades disponibles.");
                }
            }
        }.execute();

        return view;
    }


    public void obtenerGeolocalizacion() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fs.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null && map != null) {
                            // Configurar la ubicación actual en el mapa
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            map.addMarker(new MarkerOptions().position(currentLocation).title("Ubicación actual"));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
                        } else {
                            Toast.makeText(getContext(), "No se pudo obtener la ubicación actual", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al obtener ubicación: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(getContext(), "Permisos de geolocalización denegados", Toast.LENGTH_SHORT).show();
        }
    }

    public void setPermisosGeoloc() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (map != null) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    map.setMyLocationEnabled(true);
                    obtenerGeolocalizacion();
                }
            } else {
                Toast.makeText(getContext(), "Permisos denegados", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void actualizarMapa(List<Camara> camaras, List<Incidencia> incidencias) {
        if (map == null) {
            Log.e("MapaFragment", "Mapa no inicializado.");
            return;
        }

        map.clear();

        if (camaras != null && !camaras.isEmpty()) {
            pintarCamaras(camaras);
        }

        if (incidencias != null && !incidencias.isEmpty()) {
            pintarIncidencias(incidencias);
        }


    }

    public void pintarIncidencias(List<Incidencia> incidencias) {
        if (incidencias != null && !incidencias.isEmpty()) {
            // Configura el adaptador universal solo una vez
            WindowAdapterUniversal adapterUniversal = new WindowAdapterUniversal(requireContext(), getLayoutInflater());
            map.setInfoWindowAdapter(adapterUniversal);

                for (Incidencia incidencia : incidencias) {

                    boolean esFavorito = false;
                    for (FavoritoIncidencia favorito : miListaFavoritosIncidencias) {
                        if (incidencia.getId() == favorito.getIdIncidencia()) {
                            esFavorito = true;
                            break;
                        }
                    }
                    if (esFavorito) {
                        incidencia.setImagen(R.drawable.estrella_favorito);
                        Log.d("fav", "pintarIncidencias: "+incidencia.getImagen());
                    } else {
                        incidencia.setImagen(R.drawable.estrella_check);
                        Log.d("fav", "pintarIncidencias: "+incidencia.getImagen());
                    }

                    miListaIncidencias.add(incidencia);
                }


            // Configura el listener para las incidencias
            adapterUniversal.setOnIncidenciaClickListener(new WindowAdapterUniversal.OnIncidenciaClickListener() {
                @Override
                public void onIncidenciaClick(Incidencia incidencia) {
                    if (getContext() != null && getActivity() != null) {
                        Log.d("INCIDENCIA_CLICK", "ID INCIDENCIA: " + incidencia.getId());
                        Toast.makeText(getContext(), "Incidencia seleccionada: " + incidencia.getCiudad().getNombre(), Toast.LENGTH_SHORT).show();

                        // Cambia al fragmento de detalles de incidencia
                        IncidenciaFragment incidenciaFragment = IncidenciaFragment.newInstance(incidencia);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, incidenciaFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }


                @Override
                public void onFavoritoIncidenciaClick(Incidencia incidencia) {
                    Log.d("INCIDENCIA_FAV", "IMAGEN INCIDENCIA: " + String.valueOf(incidencia.getImagen()));
                    if (incidencia.getImagen() == R.drawable.estrella_check) {
                        incidencia.setImagen(R.drawable.estrella_favorito);

                        gestorBDD.getGestorIncidenciasFavoritas().insertarFavoritosIncidencias(String.valueOf(incidencia.getId()));
                        Log.d("INCIDENCIA_FAV", "ID INCIDENCIA: " + String.valueOf(incidencia.getId()));
                    } else {
                        incidencia.setImagen(R.drawable.estrella_check);
                        gestorBDD.getGestorIncidenciasFavoritas().eliminarFavoritosIncidencias(String.valueOf(incidencia.getId()));
                    }

                    Marker marker = findMarkerForIncidencia(incidencia);

                    if (marker != null) {
                        // Actualizar el icono del marcador
                        int imagenId = incidencia.getImagen();
                        if (imagenId != 0) {
                            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), imagenId);
                            if (originalBitmap != null) {
                                Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 50, 50, false);
                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(resizedBitmap));
                                // Notificar que se ha actualizado el ícono
                                Log.d("MapaFragment", "Icono actualizado para la cámara: " + incidencia.getCarretera());
                            } else {
                                Log.e("MapaFragment", "Error al cargar la imagen: recurso nulo.");
                            }
                        } else {
                            Log.e("MapaFragment", "ID de imagen inválido: " + imagenId);
                        }
                    } else {
                        Log.e("MapaFragment", "No se encontró el marcador para la cámara: " + incidencia.getId());
                    }

                    Toast.makeText(getContext(), "Favorito actualizado: " + incidencia.getCarretera(), Toast.LENGTH_SHORT).show();

                }


            });

            // Itera y agrega los marcadores al mapa
            for (Incidencia in : incidencias) {
                Log.d("MapaFragment", "Cargando incidencia: " + in);

                try {
                    // Validar latitud y longitud
                    double lat = Double.parseDouble(in.getLatitud());
                    double lng = Double.parseDouble(in.getLongitud());
                    LatLng punto = new LatLng(lat, lng);

                    // Crear el icono redimensionado
                    Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.incidencias);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 50, 50, false);

                    // Agregar marcador al mapa
                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(punto)
                            .icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));

                    // Asignar la incidencia como tag al marcador
                    marker.setTag(in);
                    markersList.add(marker);

                } catch (NumberFormatException e) {
                    Log.e("MapaFragment", "Coordenadas inválidas para la incidencia: " + (in != null ? in.getId() : "null"), e);
                } catch (Exception e) {
                    Log.e("MapaFragment", "Error al procesar la incidencia: " + in, e);
                }
            }
        } else {
            Log.d("MapaFragment", "No hay incidencias para pintar.");
        }
    }




    @SuppressLint("StaticFieldLeak")
    public void pintarIncidencias() {
        new PeticionesIncidencias.ObtenerTodasLasIncidencias() {
            @Override
            protected void onPostExecute(List<Incidencia> incidencias) {
                super.onPostExecute(incidencias);
                if (incidencias != null && !incidencias.isEmpty()) {
                    incidenciasResultado[0] = incidencias;
                    pintarIncidencias(incidencias);
                }
            }
        }.execute();
    }


    public void pintarCamaras(List<Camara> camaras) {
        if (camaras != null && !camaras.isEmpty()) {
            WindowAdapterUniversal adapterUniversal = new WindowAdapterUniversal(requireContext(), getLayoutInflater());
            map.setInfoWindowAdapter(adapterUniversal);

            for (Camara camara : camaras) {

                boolean esFavorito = false;
                for (FavoritoCamara favorito : miListaFavoritosCamaras) {
                    if (camara.getId() == favorito.getIdCamara()) {
                        esFavorito = true;
                        break;
                    }
                }


                if (esFavorito) {
                    camara.setImagen(R.drawable.estrella_favorito);
                } else {
                    camara.setImagen(R.drawable.estrella_check);
                }

                miListaCamaras.add(camara);
            }

            // Configura el listener para las cámaras
            adapterUniversal.setOnCamaraClickListener(new WindowAdapterUniversal.OnCamaraClickListener() {
                @Override
                public void onCamaraClick(Camara camara) {
                    if (getContext() != null && getActivity() != null) {
                        Toast.makeText(getContext(), "Cámara seleccionada: " + camara.getNombre(), Toast.LENGTH_SHORT).show();
                        // Cambia al fragmento de detalles de cámara
                        CamaraFragment camaraFragment = CamaraFragment.newInstance(camara);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, camaraFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }

                @Override
                public void onFavoritoCamaraClick(Camara camara) {
                    // Cambia el estado de favorito de la cámara
                    Log.d("Camara", "IMAGEN CAMARA: " + String.valueOf(camara.getImagen()));
                    if (camara.getImagen() == R.drawable.estrella_check) {
                        camara.setImagen(R.drawable.estrella_favorito);
                        gestorBDD.getGestorCamarasFavoritas().insertarFavoritosCamaras(String.valueOf(camara.getId()));
                        Log.d("Camara", "ID CAMARA: " + String.valueOf(camara.getId()));
                    } else {
                        camara.setImagen(R.drawable.estrella_check);
                        gestorBDD.getGestorCamarasFavoritas().eliminarFavoritosCamaras(String.valueOf(camara.getId()));
                    }

                    Marker marker = findMarkerForCamara(camara);

                    if (marker != null) {
                        int imagenId = camara.getImagen();
                        if (imagenId != 0) {
                            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), imagenId);
                            if (originalBitmap != null) {
                                Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 50, 50, false);
                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(resizedBitmap));
                                Log.d("MapaFragment", "Icono actualizado para la cámara: " + camara.getNombre());
                            } else {
                                Log.e("MapaFragment", "Error al cargar la imagen: recurso nulo.");
                            }
                        } else {
                            Log.e("MapaFragment", "ID de imagen inválido: " + imagenId);
                        }
                    } else {
                        Log.e("MapaFragment", "No se encontró el marcador para la cámara: " + camara.getId());
                    }

                    Toast.makeText(getContext(), "Favorito actualizado: " + camara.getNombre(), Toast.LENGTH_SHORT).show();
                }

            });

            // Itera y agrega los marcadores al mapa
            for (Camara camara : camaras) {
                try {
                    // Validar latitud y longitud
                    double lat = Double.parseDouble(camara.getLatitud());
                    double lng = Double.parseDouble(camara.getLongitud());
                    LatLng punto = new LatLng(lat, lng);

                    // Crear el icono redimensionado
                    Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.camaras);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 50, 50, false);

                    // Agregar marcador al mapa
                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(punto)
                            .icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));

                    // Asignar la cámara como tag al marcador
                    marker.setTag(camara);

                    // Agregar el marcador a la lista
                    markersList.add(marker);

                } catch (NumberFormatException e) {
                    Log.e("MapaFragment", "Coordenadas inválidas para la cámara: " + (camara != null ? camara.getId() : "null"), e);
                } catch (Exception e) {
                    Log.e("MapaFragment", "Error al procesar la cámara: " + camara, e);
                }
            }
        } else {
            Log.d("MapaFragment", "No hay cámaras para pintar.");
        }
    }

    private Marker findMarkerForCamara(Camara camara) {
        for (Marker marker : markersList) {
            if (marker.getTag() instanceof Camara && ((Camara) marker.getTag()).getId() == camara.getId()) {
                return marker;
            }
        }
        return null;
    }

    private Marker findMarkerForIncidencia(Incidencia incidencia) {
        for (Marker marker : markersList) {
            if (marker.getTag() instanceof Incidencia && ((Incidencia) marker.getTag()).getId() == incidencia.getId()) {
                return marker;
            }
        }
        return null;
    }


    @SuppressLint("StaticFieldLeak")
    public void pintarCamaras() {
        new PeticionesCamaras.ObtenerTodasLasCamaras() {
            @Override
            protected void onPostExecute(List<Camara> camaras) {
                super.onPostExecute(camaras);
                if (camaras != null && !camaras.isEmpty()) {
                    camarasResultado[0] = camaras;
                    pintarCamaras(camaras);
                }
            }
        }.execute();
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        pintarIncidencias();
        pintarCamaras();

        map.getUiSettings().setZoomControlsEnabled(true);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);

        } else {
            setPermisosGeoloc();
        }


    }
}
