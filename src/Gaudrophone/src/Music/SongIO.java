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
package Music;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class SongIO {
    private double lastChordLength = 0;
    
    public Song Load(String path) {
        LinkedList<String> lines = this.readFile(path);
        lines = this.stripSong(lines);
        return this.readSong(lines);
    }
    
    private LinkedList<String> readFile(String path) {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            LinkedList<String> lines = new LinkedList<>();
            String line = br.readLine();
            
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
            
            return lines;
        } catch (Exception ex) {
            return null;
        }
    }
    
    private LinkedList<String> stripSong(LinkedList<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            //remove comments
            if (lines.get(i).contains("//")) {
                lines.remove(i);
            } else {
                //lines.set(i, lines.get(i).replace("\t", "").replace("|", "").replace(" ", "").replace("*", ""));
                lines.set(i, lines.get(i).replaceAll("[\t *\\|]", ""));
            }
        }
        return lines;
    }
    
    private Song readSong(LinkedList<String> lines) {
        Song song = new Song();
        int i = 0;
        
        while (i < lines.size()) {
            if (song.getBPM() == -1) {
                if (!"".equals(lines.get(i))) {
                    try {
                        song.setBPM(Integer.parseInt(lines.get(i)));
                    } catch (NumberFormatException ex) {}
                }
                i++;
                continue;
            }
            
            if ("".equals(lines.get(i))) {
                i++;
                continue;
            }

            LinkedList<String> chordLines = new LinkedList<>();
            do {
                chordLines.add(lines.get(i));
                i++;
            } while (!"".equals(lines.get(i)) || i < lines.size());
            
            this.readChord(chordLines, song);
        }
        return song;
    }
    
    private void readChord(LinkedList<String> lines, Song song) {
        if (!lines.isEmpty()) {
            LinkedList<Double> tempo = null;
            if (lines.get(lines.size()-1).matches("[_,.\\d]+")) {
                tempo = new LinkedList<>();
                for (char c: lines.removeLast().toCharArray()) {
                    tempo.add(this.stringToStep(c));
                }
            }
        }
    }
    
    private double stringToStep(char c) {
        switch (c) {
            case '_':
                return 1;
            case ',':
                return 0.5;
            case '.':
                return 0.25;
            default:
                try {
                    return Double.parseDouble(String.valueOf(c));
                } catch (NumberFormatException ex) {
                    return 1;
                }
        }
    }
}