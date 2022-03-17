import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        int sizeArray = size();
        if (sizeArray != 0 ) {
            Arrays.fill(storage, 0, sizeArray, null);
        }
    }

    void save(Resume r) {
        storage[size()] = r;
    }

    Resume get(String uuid) {
        int sizeArray = size();
        for (int i = 0; i < sizeArray; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        int sizeArray = size();
        for (int i = 0; i < sizeArray; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                if (sizeArray - 1 - i >= 0) {
                    System.arraycopy(storage, i + 1, storage, i, sizeArray - 1 - i);
                }
                storage[sizeArray - 1] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size());
    }

    int size() {
        int sizeStorage = 0;
        for (Resume resume : storage) {
            if (resume != null) {
                sizeStorage++;
            } else break;
        }
        return sizeStorage;
    }
}
