package game.serialization;

public class RangedIntegerSerializer implements Serializer<Integer>{
    private final int min, max;
    public RangedIntegerSerializer(int min, int max){
        this.max = max;
        this.min = min;
    }

    @Override
    public String serialize(Integer value) {
        if (value < min || value >= max){
            throw new InvalidSerializedDataException();
        }
        return ""+value;
    }

    @Override
    public Integer deserialize(String representation) {
        int parsed = Integer.parseInt(representation);
        if (parsed < min || parsed >= max){
            throw new InvalidSerializedDataException();
        }
        return parsed;
    }

    @Override
    public String define() {
        return "Integer($min,$max)";
    }

    @Override
    public int separationLevel() {
        return 0;
    }
}