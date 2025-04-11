package com.elingenio.Proyecto.Services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.elingenio.Proyecto.Modelo.CustomerData;
import com.elingenio.Proyecto.Repository.CustomerDataRepository;
import com.elingenio.Proyecto.Services.dto.PredictionCustomer;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CustomerClassificationService {

    private static final Logger LOGGER = Logger.getLogger(CustomerClassificationService.class.getName());
    private Classifier classifier;
    private Instances dataStructure;
    private final CustomerDataRepository repository;

    public CustomerClassificationService(CustomerDataRepository repository) {
        this.repository = repository;
        try {
            ClassPathResource modelResource = new ClassPathResource("customer_classification.model");
            classifier = (Classifier) weka.core.SerializationHelper.read(modelResource.getInputStream());
            LOGGER.info("Modelo de clasificación de clientes cargado exitosamente.");

            ClassPathResource arffResource = new ClassPathResource("clasificacion_clientes.arff");
            DataSource source = new DataSource(arffResource.getInputStream());
            dataStructure = source.getDataSet();
            dataStructure.setClassIndex(dataStructure.numAttributes() - 1);
            LOGGER.info("Estructura clasificacion_clientes.arff cargada exitosamente.");
        } catch (Exception e) {
            LOGGER.severe("Error al inicializar CustomerClassificationService: " + e.getMessage());
            throw new RuntimeException("No se pudo inicializar el servicio de clasificación", e);
        }
    }

    public PredictionCustomer classifyAndSave(CustomerData datos) throws Exception {
        LOGGER.info("Datos recibidos: " + datos.toString());

        Instance instance = new DenseInstance(6);
        instance.setDataset(dataStructure);
        instance.setValue(0, datos.getEdad());
        instance.setValue(1, datos.getFrecuenciaCompra());
        instance.setValue(2, datos.getValorTotalCompra());
        instance.setValue(3, datos.getUltimaCompra());
        instance.setValue(4, datos.getMetodoPago());

        double predictionValue = classifier.classifyInstance(instance);
        String classification = dataStructure.classAttribute().value((int) predictionValue);

        double[] probabilities = classifier.distributionForInstance(instance);
        double confidence = probabilities[(int) predictionValue];
        DecimalFormat df = new DecimalFormat("#.#");
        String confidencePercentage = df.format(confidence * 100) + "%";

        datos.setCategoriaCliente(classification);
        repository.save(datos);

        return new PredictionCustomer(classification, confidencePercentage);
    }

    public List<CustomerData> getAllPredictions() {
        return repository.findAll();
    }

    public void logError(String message, Exception e) {
        LOGGER.severe(message + ": " + e.getMessage());
    }
}