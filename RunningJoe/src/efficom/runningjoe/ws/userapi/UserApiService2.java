package efficom.runningjoe.ws.userapi;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Dictionary;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import efficom.runningjoe.RunningJoe;
import javafx.util.Pair;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class UserApiService2
{
    private static final String	NAMESPACE	= "http://efficom/runningjoe/ws/UserApi/";
    //L'URL suivante ne peut pas être localhost car localhost représente l'émulateur
    private static final String	URL	= "http://runningjoe.grimfor.fr/ws/UserApi";

    public static Object soap (String method, String action, Map<String, String> mapParam) throws IOException, XmlPullParserException
    {
        // Création de la requête SOAP
        SoapObject request = new SoapObject (NAMESPACE, method);
        //Ajout de propriété: addProperty(nom de variable, valeur) -> Le nom de la variable vient du fichier WSDL
        for ( Map.Entry<String, String> entry : mapParam.entrySet() ) {
            request.addProperty(entry.getKey(), entry.getValue());
        }

        //Toutes les données demandées sont mises dans une enveloppe.
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope (
                SoapEnvelope.VER11);
        //Préparation de la requête
        envelope.setOutputSoapObject (request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE (URL);
        //Ceci est optionnel, on l'utilise pour savoir si nous voulons ou non utiliser
        //un paquet "sniffer" pour vérifier le message original (androidHttpTransport.requestDump)
        androidHttpTransport.debug = true;
        //Envoie de la requête
        androidHttpTransport.call (action, envelope);
        //Obtention du résultat

        Object soapResult = envelope.getResponse();
        return soapResult;
    }
}
