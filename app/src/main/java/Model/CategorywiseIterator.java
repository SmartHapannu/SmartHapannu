package Model;

import java.util.Iterator;
import java.util.List;

public class CategorywiseIterator<CategoryWiseResult> implements Iterable<CategoryWiseResult> {

    //private CategoryWiseResult[] categorywiseList;
    private List<Model.CategoryWiseResult> categorywiseList;
    private int currentSize;

    public CategorywiseIterator(List<Model.CategoryWiseResult> newArray) {
        this.categorywiseList = newArray;
        this.currentSize = categorywiseList.size();
    }

    public List<Model.CategoryWiseResult> getCategorywiseList() {
        return categorywiseList;
    }

    public void setCategorywiseList(List<Model.CategoryWiseResult> categorywiseList) {
        this.categorywiseList = categorywiseList;
    }

    @Override

    public Iterator<CategoryWiseResult> iterator() {
        Iterator<CategoryWiseResult> it = new Iterator<CategoryWiseResult>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < categorywiseList.size() && categorywiseList.get(currentIndex) != null;
            }

            @Override
            public CategoryWiseResult next() {
                return (CategoryWiseResult) categorywiseList.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
}