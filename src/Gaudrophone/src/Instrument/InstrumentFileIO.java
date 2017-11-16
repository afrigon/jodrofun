/*
 * The MIT License
 *
 * Copyright 2017 frigon.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package Instrument;

//import java.beans.XMLEncoder;
import KeyUtils.KeyShape;
import KeyUtils.Vector2;
import Music.Sound;
import Music.SynthesizedSound;
import java.beans.XMLDecoder;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class InstrumentFileIO {
    private String path = null;
    
    public InstrumentFileIO(String path) {
        this.path = path;
    }
    
    public boolean save(Instrument instrument) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            Element rootElement = doc.createElement("Instrument");
            rootElement.setAttribute("name", instrument.getName());
            doc.appendChild(rootElement);

            Element keysElement = doc.createElement("Keys");
            rootElement.appendChild(keysElement);
            
            ArrayList<Key> keys = instrument.getKeys();
            for (Key key: keys) {
                Element keyElement = doc.createElement("Key");
                keyElement.setAttribute("name", key.getName());
                keysElement.appendChild(keyElement);
                
                
                Element soundElement = doc.createElement("Sound");
                Sound sound = key.getSound();
                keyElement.appendChild(soundElement);
                 
                Element volume = doc.createElement("Volume");
                volume.appendChild(doc.createTextNode(String.valueOf(sound.getVolume())));
                soundElement.appendChild(volume);
                
                if (SynthesizedSound.class.isInstance(sound)) {
                    soundElement.setAttribute("type", "SynthesizedSound");
                    
                    Element frequency = doc.createElement("Frequency");
                    frequency.appendChild(doc.createTextNode(String.valueOf(((SynthesizedSound)sound).getFrequency())));
                    soundElement.appendChild(frequency);
                    
                    Element tuning = doc.createElement("Tuning");
                    tuning.appendChild(doc.createTextNode(String.valueOf(((SynthesizedSound)sound).getTuning())));
                    soundElement.appendChild(tuning);
                }
		
                
                Element shapeElement = doc.createElement("Shape");
                KeyShape shape = key.getShape();
                keyElement.appendChild(shapeElement);
                
                Element pointsElement = doc.createElement("Points");
                shapeElement.appendChild(pointsElement);
                
                for (Vector2 point: shape.getPoints()) {
                    Element pointElement = doc.createElement("Point");
                    pointElement.setAttribute("x", String.valueOf(point.getX()));
                    pointElement.setAttribute("y", String.valueOf(point.getY()));
                    pointsElement.appendChild(pointElement);
                }
                
                keysElement.appendChild(keyElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(this.path));
            transformer.transform(source, result);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public Instrument load() {
        return null;
    }
}
