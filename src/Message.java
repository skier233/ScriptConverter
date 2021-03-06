import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//This represents a message. It stores an arraylist of message components because
//a message can have plain text it outputs (a phrase) or it can use random text (RT)
//or several other functions
public class Message extends LineComponent
{
    public ArrayList<LineComponent> messageComponents;
    public Message(String message)
    {
        super(message);
        messageComponents = new ArrayList<LineComponent>();
        String phraseComponent = "(" + RegexHelper.hashFunction + "|" + RegexHelper.simplePhrase + "|" + RegexHelper.randomText + "|" + RegexHelper.formatter + "|" + RegexHelper.followUp + ")";
        Matcher phraseComponentMatcher = Pattern.compile(phraseComponent).matcher(message);
        while (phraseComponentMatcher.find())
        {
            if (phraseComponentMatcher.group().matches(RegexHelper.simplePhrase))
            {
                messageComponents.add(new Phrase(phraseComponentMatcher.group()));
            }
            else if (phraseComponentMatcher.group().matches(RegexHelper.randomText))
            {
                messageComponents.add(new RandomText(phraseComponentMatcher.group()));
            }
            else if (phraseComponentMatcher.group().matches(RegexHelper.formatter))
            {
                messageComponents.add(new Formatter(phraseComponentMatcher.group()));
            }
            else if (phraseComponentMatcher.group().matches(RegexHelper.followUp))
            {
                messageComponents.add(new FollowUp(phraseComponentMatcher.group()));
            }
            else if (phraseComponentMatcher.group().matches(RegexHelper.hashFunction))
            {
                messageComponents.add(new HashFunction(phraseComponentMatcher.group()));
            }
            else 
            {
                messageComponents.add(new Phrase(phraseComponentMatcher.group()));
            }
        }
    }
    public String toString()
    {
        String toReturn = "Message";
        for(LineComponent comp: messageComponents)
        {
            toReturn += "\n" + comp.toString();
        }
        return toReturn;
        //return "Message:" + content;
    }
}
