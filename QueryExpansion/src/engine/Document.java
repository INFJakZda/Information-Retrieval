package engine;

import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.TokenizerME;

import java.util.*;

public class Document
{
    public String _title;
    public String _content;
    public int _ID;

    public double _bow_representation[];
    public double _tf_representation[];
    public double _tf_idf_representation[];

    public ArrayList <String> _terms;

    public Document(String title, String content, int id)
    {
        this._title = title;
        this._content = content;
        this._ID = id;
    }

    public void print()
    {
        System.out.print(_title);
        System.out.print(_content);
    }

    private String[] getTokenizedAndNormalized(TokenizerME tokenizer, PorterStemmer stemmer)
    {
        String tokens[] = tokenizer.tokenize(_content);
        for (int i = 0; i < tokens.length; i++)
            tokens[i] = stemmer.stem(tokens[i].toLowerCase());
        return tokens;
    }

    public void doProcessing(Dictionary dictionary, TokenizerME tokenizer, PorterStemmer stemmer)
    {
        String tokenizedAndNormalized[] = getTokenizedAndNormalized(tokenizer, stemmer);

        Set <String> fTokens = new HashSet <>();
        for (String s : tokenizedAndNormalized)
            if (dictionary._terms.contains(s))
                fTokens.add(s);

        _terms = new ArrayList <>(fTokens.size());
        _terms.addAll(fTokens);
        Collections.sort(_terms);
    }

    public void computeVectorRepresentations(Dictionary dictionary, TokenizerME tokenizer, PorterStemmer stemmer)
    {
        String tokenizedAndNormalized[] = getTokenizedAndNormalized(tokenizer, stemmer);

        // TODO compute bow representation
        // 1) iterate over tokenizedAndNormalized
        // 2) check if a dictionary._terms contains a given term
        // 3) update bag-of-words vector. Use dictionary.termID to get term's position
        _bow_representation = new double[dictionary._terms.size()];
        // -----------------------------------------------

        // -----------------------------------------------


        // TODO compute TF representation
        // use _terms.size() and _bow_representation vector
        _tf_representation = new double[dictionary._terms.size()];
        // -----------------------------------------------

        // -----------------------------------------------


        // TODO compute tf-idf representation
        // use _tf_representation vector and dictionary._idf()
        _tf_idf_representation = new double[dictionary._terms.size()];
        // -----------------------------------------------

        // -----------------------------------------------

        /*for (int i = 0; i < _tf_idf_representation.length; i++)
            System.out.print(_bow_representation[i] + " " );
        System.out.println("");
        for (int i = 0; i < _tf_idf_representation.length; i++)
            System.out.print(_tf_representation[i] + " " );
        System.out.println("");
        for (int i = 0; i < _tf_idf_representation.length; i++)
            System.out.print(_tf_idf_representation[i] + " " );*/
    }
}
