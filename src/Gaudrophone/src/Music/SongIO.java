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
    private double lastChordLength = 0.25;
    
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
        for (int i = 0; i < lines.size(); ++i) {
            //trim unsignificent char and comments (any //+)
            lines.set(i, lines.get(i).replaceAll("[\t *\\|]|([//].*)", ""));
        }
        return lines;
    }
    
    private Song readSong(LinkedList<String> lines) {
        Song song = new Song();
        song.setRaw(String.join("\n", lines));
        int i = 0;
        
        song.addChord(new Silence(this.lastChordLength, 0));
        
        while (i < lines.size()) {
            LinkedList<String> chordLines = new LinkedList<>();
            if (song.getBPM() == -1) {
                //find the song's BPM
                if (!"".equals(lines.get(i))) {
                    try {
                        song.setBPM(Integer.parseInt(lines.get(i)));
                    } catch (NumberFormatException ex) {}
                }
                i++;
                continue;
            }
            
            //skip blank lines
            if ("".equals(lines.get(i))) {
                i++;
                continue;
            }

            //load each section
            do {
                if(!"".equals(lines.get(i))) {
                    chordLines.add(lines.get(i));
                }
                i++;
            } while (i < lines.size());
            this.readChord(chordLines, song);
        }
        
        //silence at the end
        song.addChord(new Silence(1, this.lastChordLength));
        
        return song;
    }
    
    private void readChord(LinkedList<String> lines, Song song) {
        boolean paragraphCompleted = true;
        java.util.ArrayList<java.lang.Integer> paragraphCount = new java.util.ArrayList<>();
        if (!lines.isEmpty()) {
            //get tempo for each note
            LinkedList<Double> tempo = new LinkedList<>();
            for(int i = 0 ; i < lines.size(); ) {
                if (paragraphCompleted && lines.get(i).toUpperCase().matches("[ABCDEBGX].*")) {
                    paragraphCompleted = false;
                    paragraphCount.add(1);
                    ++i;
                } else if (!paragraphCompleted && lines.get(i).toUpperCase().matches("[ABCDEBGX].*")) {
                    paragraphCount.set(paragraphCount.size() - 1, paragraphCount.get(paragraphCount.size() - 1) + 1);
                    ++i;
                } else if (lines.get(i).matches("[_,.\\d]+")) {
                    paragraphCompleted = true;
                    for (char c: lines.get(i).toCharArray()) {
                        tempo.add(this.stringToStep(c));
                    }
                    lines.remove(lines.get(i));
                } else {
                    ++i;
                }
            }
            
            int beforeChordCount = song.getChords().size();
            
            //and here miracle occurs
            int linesBefore = 0;
            int nbrOctaves = 0;
            PlayableChord chord = null;
            for (int j = 0; j < paragraphCount.size(); ++j) {
                for (int i = 0; i < lines.get(linesBefore).toCharArray().length; i++) {
                    String value = String.valueOf(lines.get(linesBefore).toCharArray()[i]).toUpperCase();
                    if (value.matches("[ABCDEFGX]")) {
                        if (chord != null) { song.addChord(chord); }
                        chord = new PlayableChord();
                        if (!tempo.isEmpty()) {
                            chord.setLength(tempo.get(i - nbrOctaves));
                        }
                        chord.setRelativeSteps(this.lastChordLength);
                        this.lastChordLength = chord.getLength();

                        if (!"X".equals(value)) {
                            chord.addNote(new PlayableNote(Note.getNoteFromName(value), 4));
                        }
                    } else if (value.matches("[\\d]")) {
                        try {
                            if (chord != null && !chord.isEmpty()) {
                                chord.getNotes().getLast().setOctave(Integer.parseInt(value));
                                ++nbrOctaves;
                            }
                        } catch (NumberFormatException ex) {}
                    } else if ("#".equals(value)) {
                        if (chord != null && !chord.isEmpty()) {
                            chord.getNotes().getLast().setAlteration(Alteration.Sharp);    
                        }
                    }
                }
                linesBefore += paragraphCount.get(j);
                nbrOctaves = 0;
            }
            song.addChord(chord);
            linesBefore = 0;
            //the rest
            for(int y = 0; y < paragraphCount.size(); ++y) {
                for (int i = 1; i < paragraphCount.get(y); ++i) {
                    String line = lines.get(i + linesBefore);
                    int j = beforeChordCount - 1 + y * (song.getChords().size() / paragraphCount.size());
                    for (char c: line.toCharArray()) {
                        String value = String.valueOf(c).toUpperCase();
                        if (value.matches("[ABCDEFGX]")) {
                            j++;
                            if (!"X".equals(value)) {
                                song.getChords().get(j).addNote(new PlayableNote(Note.getNoteFromName(value), 4));
                            }
                        } else if (value.matches("[\\d]")) {
                            try {
                                song.getChords().get(j).getNotes().getLast().setOctave(Integer.parseInt(value));
                            } catch (NumberFormatException ex) {}
                        } else if ("#".equals(value)) {
                            song.getChords().get(j).getNotes().getLast().setAlteration(Alteration.Sharp);
                        }
                    }
                }
                linesBefore += paragraphCount.get(y);
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