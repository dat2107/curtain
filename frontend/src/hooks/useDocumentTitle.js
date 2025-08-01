import { useEffect } from 'react';

const useDocumentTitle = (title) => {
  useEffect(() => {
    document.title = title;
    return () => {
      document.title = 'Elegance Curtains';
    };
  }, [title]);
};

export default useDocumentTitle;