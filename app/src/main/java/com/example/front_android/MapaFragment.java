package com.example.front_android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
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
    private List<Region> regiones;
    private List<Ciudad> ciudades;
    private List<TipoIncidencia> tipoIncidencias;
    private List<Provincia> provincias;
    public MapaFragment() {
        // Constructor requerido vacío
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        fs = LocationServices.getFusedLocationProviderClient(getContext());
        selectRegion = view.findViewById(R.id.spinnerRegion);
        selectProvincia = view.findViewById(R.id.spinnerProvincia);
        selectCiudad = view.findViewById(R.id.spinnerCiudad);
        selectTipoIncidencia = view.findViewById(R.id.spinnerTipoIncidencia);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(getContext(), "Error al cargar el mapa", Toast.LENGTH_SHORT).show();
        }

        setPermisosGeoloc();


        selectRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("MapaFragment", "Region seleccionada: " + position);

                if (regiones != null && !regiones.isEmpty() && position > 0) {
                    Region regionSeleccionada = regiones.get(position - 1);
                    int regionId = regionSeleccionada.getIdRegion();
                    Log.d("MapaFragment", "ID de region seleccionada: " + regionId);

                    final List<Camara>[] camarasResultado = new List[1];
                    final List<Incidencia>[] incidenciasResultado = new List[1];

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

                    final List<Camara>[] camarasResultado = new List[1];
                    final List<Incidencia>[] incidenciasResultado = new List[1];

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
                    Log.d("MapaFragment", "ID de ciudad seleccionada: " + ciudadId);
                    Log.d("MapaFragment", "nombre de ciudad seleccionada: " + ciudadSelected.getNombre());

                    final List<Camara>[] camarasResultado = new List[1];
                    final List<Incidencia>[] incidenciasResultado = new List[1];

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

                    final List<Camara>[] camarasResultado = new List[1];
                    final List<Incidencia>[] incidenciasResultado = new List[1];

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

                    miListaRegiones.add("Regiones");
                    for (Region region : regiones) {
                        miListaRegiones.add(region.getNombreEs());
                    }

                    adapterRegion.notifyDataSetChanged();
                }
            }
        }.execute();

        new PeticionesCiudades.ObtenerTodasLasCiudades() {
            @Override
            protected void onPostExecute(List<Ciudad> ciudades) {
                super.onPostExecute(ciudades);

                if (ciudades != null && !ciudades.isEmpty()) {
                    MapaFragment.this.ciudades = ciudades;

                    miListaCiudades.add("Ciudades");
                    for (Ciudad ciudad : ciudades) {
                        miListaCiudades.add(ciudad.getNombre());

                    }

                    adapterCiudad.notifyDataSetChanged();
                }
            }
        }.execute();

        new PeticionesProvincias.ObtenerTodasLasProvincias() {
            @Override
            protected void onPostExecute(List<Provincia> provincias) {
                super.onPostExecute(provincias);

                if (provincias != null && !provincias.isEmpty()) {
                    MapaFragment.this.provincias = provincias;

                    miListaProvincias.add("Provincias");
                    for (Provincia provincia : provincias) {
                        miListaProvincias.add(provincia.getNombre());

                    }
                    adapterProvincia.notifyDataSetChanged();
                }
            }
        }.execute();

        new PeticionesTiposDeIncidencia.ObtenerTodasLosTiposDeIncidencia() {
            @Override
            protected void onPostExecute(List<TipoIncidencia> tipoIncidencias) {
                super.onPostExecute(tipoIncidencias);

                if (tipoIncidencias != null && !tipoIncidencias.isEmpty()) {
                    MapaFragment.this.tipoIncidencias = tipoIncidencias;

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
            for (Incidencia in : incidencias) {
                Log.d("MapaFragment", "cargando" + in);
                try {
                    double lat = Double.parseDouble(in.getLatitud());
                    double lng = Double.parseDouble(in.getLongitud());
                    LatLng punto = new LatLng(lat, lng);

                    Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.incidencias);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 50, 50, false);

                    map.setInfoWindowAdapter(new WindowAdapterUniversal(getLayoutInflater()));
                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(punto)
                            .icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));

                    marker.setTag(in);
                } catch (NumberFormatException e) {
                    Log.e("MapaFragment", "Coordenadas inválidas para la incidencia: " + in.getId(), e);
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
                    pintarIncidencias(incidencias);
                }
            }
        }.execute();
    }


    public void pintarCamaras(List<Camara> camaras) {

        if (camaras != null && !camaras.isEmpty()) {
            for (Camara camara : camaras) {
                try {
                    double lat = Double.parseDouble(camara.getLatitud());
                    double lng = Double.parseDouble(camara.getLongitud());
                    LatLng punto = new LatLng(lat, lng);

                    Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.camaras);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 50, 50, false);

                    map.setInfoWindowAdapter(new WindowAdapterUniversal(getLayoutInflater()));
                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(punto)
                            .icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));

                    marker.setTag(camara);
                } catch (NumberFormatException e) {
                    Log.e("MapaFragment", "Coordenadas inválidas para la cámara: " + camara.getId(), e);
                }
            }
        } else {
            Log.d("MapaFragment", "No hay cámaras para pintar.");
        }
    }



    @SuppressLint("StaticFieldLeak")
    public void pintarCamaras() {
        new PeticionesCamaras.ObtenerTodasLasCamaras() {
            @Override
            protected void onPostExecute(List<Camara> camaras) {
                super.onPostExecute(camaras);
                if (camaras != null && !camaras.isEmpty()) {
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
